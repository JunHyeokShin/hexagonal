# 모듈 간 통신 전략

> 이벤트(비동기) vs 동기 호출 — 언제 무엇을 쓰고, 헥사고날 구조에서 어떻게 결합을 격리하는가.

이 프로젝트는 `order` / `inventory` / `notification` 세 모듈이 **도메인 이벤트**(Spring Modulith `@ApplicationModuleListener`)로 느슨하게 통신한다. 하지만 이벤트는 만능이 아니며, 구조적으로 처리할 수 없는 경우가 있다.

## 1. 이벤트로 안 되는 경우

이벤트는 비동기 단방향 통지(fire-and-forget)다. 다음은 이벤트로 풀 수 없다.

| 상황 | 왜 이벤트로 안 되나 |
|------|--------------------|
| **즉시 결과가 필요** (주문 시점에 재고 확인 후 바로 거부) | 이벤트는 단방향 통지라 발행자가 응답을 받지 못함 |
| **질의(Query)** ("현재 재고 몇 개야?") | 이벤트는 "일어난 사실"을 알릴 뿐, 물어볼 수 없음 |
| **강한 일관성(원자성)** (주문+재고차감을 한 트랜잭션에) | `@ApplicationModuleListener`는 커밋 후 별도 트랜잭션 → 최종 일관성만 보장 |
| **요청-응답** (작업을 시키고 결과를 받아야 함) | 동기 반환값이 없음 |

현재 이 프로젝트는 첫 번째 케이스의 한계를 안고 있다. 주문이 항상 `201`로 성공하고, 재고 부족은 사후에 보상해야 한다(사가 미완성).

## 2. 대안 — 동기 모듈 간 호출 (헥사고날답게 격리)

다른 모듈을 **동기 호출**하면 된다. Spring Modulith는 순환만 아니면 모듈 간 직접 의존을 허용한다. 핵심은 *어떻게* 호출하느냐다. 다른 모듈의 서비스를 그대로 주입하면 강결합되므로, **아웃바운드 포트 + 어댑터로 결합을 격리**한다.

### 구조: "주문 시 재고를 즉시 확인"

```
order 모듈                                    inventory 모듈
─────────                                     ─────────────
OrderService
   │ depends on
   ▼
[application.port.out.StockCheckPort]  ◀── order가 정의한 자기 포트(인터페이스)
   ▲ implements
   │
[adapter.out.inventory.InventoryStockCheckAdapter]
   │ calls (동기)
   └──────────────────────────────────▶ [application.port.in.CheckStockUseCase]
                                              ▲ implements
                                          InventoryService
```

- `order.application`은 자기 포트 `StockCheckPort`에만 의존 → 도메인/애플리케이션은 `inventory`의 존재를 **모름**.
- 모듈 간 실제 결합은 `order`의 **아웃바운드 어댑터 한 곳**에만 격리된다.
- `inventory`는 공개 인바운드 포트(`CheckStockUseCase`)를 Open Host API로 노출.
- 나중에 `inventory`를 외부 마이크로서비스 REST 호출로 바꿔도 `order`의 코어는 그대로다. 이벤트 어댑터와 같은 패턴이고, 단지 비동기 발행 대신 동기 호출일 뿐이다.

## 3. 코드 예시

경로는 리팩토링 후 `application.port` 기준.

### ① `inventory` — 동기 질의용 공개 인바운드 포트 (Open Host API)

```java
// inventory/application/port/in/CheckStockUseCase.java
public interface CheckStockUseCase {
  boolean hasEnough(String productId, int quantity);
}
```

```java
// inventory/application/InventoryService.java — 인터페이스에 구현 추가
@Override
@Transactional(readOnly = true)
public boolean hasEnough(String productId, int quantity) {
  return loadStockPort.findByProductId(productId)
      .map(stock -> stock.getQuantity() >= quantity)
      .orElse(false);
}
```

### ② `order` — 자기가 정의한 아웃바운드 포트 (inventory를 모름)

```java
// order/application/port/out/StockCheckPort.java
public interface StockCheckPort {
  boolean isAvailable(String productId, int quantity);
}
```

### ③ `order` — 아웃바운드 어댑터: 여기서만 inventory에 결합

```java
// order/adapter/out/inventory/InventoryStockCheckAdapter.java
@Component
@RequiredArgsConstructor
class InventoryStockCheckAdapter implements StockCheckPort {

  private final CheckStockUseCase checkStockUseCase;   // inventory의 공개 포트 동기 호출

  @Override
  public boolean isAvailable(String productId, int quantity) {
    return checkStockUseCase.hasEnough(productId, quantity);
  }
}
```

### ④ `order` — 유스케이스: 자기 포트만 의존, 부족하면 즉시 거부

```java
// order/application/OrderService.java
private final StockCheckPort stockCheckPort;   // 의존성 추가

@Override
@Transactional
public Long placeOrder(PlaceOrderCommand command) {
  if (!stockCheckPort.isAvailable(command.productId(), command.quantity())) {
    throw new OutOfStockException(command.productId());   // → 컨트롤러에서 400 매핑
  }
  Order saved = saveOrderPort.save(Order.place(command.productId(), command.quantity()));
  eventPublisher.publish(new OrderPlacedEvent(...));       // 실제 차감은 기존대로 이벤트
  return saved.getId();
}
```

## 4. 주의사항

### ⚠️ 순환 의존
위 예시는 `order → inventory` 방향이다. 기존 이벤트는 `inventory → order`(`OrderPlacedEvent` 구독) 방향이므로, 한 쌍의 모듈에 양방향이 생기면 `ModularityTests.verify()`가 깨진다. 그래서 **차감까지 동기로 합치지 않고 조회만 동기**로 두고, 실제 재고 차감은 기존 이벤트 흐름을 유지한다.

### ⚠️ 확인-차감 사이의 경합 (TOCTOU)
"확인(동기) 후 차감(이벤트)"은 확인과 차감 사이에 다른 주문이 끼어들 수 있어 **완전한 원자성은 아니다**. 진짜 강한 일관성이 필요하면 `StockJpaEntity`에 `@Version`(낙관적 락) 또는 비관적 락이 최종 방어선이다.

## 5. 의사결정 기준

```
결과를 즉시 받아야 하나? / 강한 일관성이 필요한가?
   ├─ 예 → 동기 호출 (아웃바운드 포트 + 어댑터로 격리)
   └─ 아니오 → 그냥 "사실"을 알리고 후속 처리를 위임하나?
            └─ 예 → 도메인 이벤트 (현재 방식)
```

- **명령 + 즉시성/원자성** → 동기 포트 호출
- **사실 통지 + 느슨한 결합 + 부수효과** → 이벤트
- **여러 모듈에 걸친 긴 흐름** → 이벤트 기반 사가 + 보상 트랜잭션
