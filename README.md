# Spring Modulith + Hexagonal (엄격한 클린 아키텍처) 예제

주문(Order) · 재고(Inventory) · 알림(Notification) 세 모듈이 **도메인 이벤트**로 협력하는
REST API 예제입니다. 각 모듈은 엄격한 헥사고날(포트-어댑터) 구조로 분리되어 있고,
모듈 경계는 Spring Modulith 로 강제·검증됩니다.

## 기술 스택

- Spring Boot 4.0.x / Java 25
- Spring Modulith 2.0.x (`starter-core`, `starter-jpa`, `docs`)
- Spring Web MVC, Spring Data JPA, Bean Validation, Lombok
- MySQL 8 (`localhost:3306/hexagonal`)

## 모듈 구성과 이벤트 흐름

```
 [order] ──OrderPlacedEvent──▶ [inventory] ──StockDeductedEvent─┐
                                     │                          ├─▶ [notification]
                                     └──OutOfStockEvent──────────┘
```

- **order**: 주문을 접수하고 `OrderPlacedEvent` 를 발행한다. 다른 모듈을 알지 못한다.
- **inventory**: `OrderPlacedEvent` 를 수신해 재고를 차감하고, 성공 시 `StockDeductedEvent`,
  부족 시 `OutOfStockEvent` 를 발행한다.
- **notification**: 위 이벤트들을 수신해 알림(로그)을 발송한다.

의존 방향이 `order → inventory → notification` 단방향이라 **순환이 없습니다**.
이벤트 타입만 모듈 경계를 넘으며, 각 모듈은 `domain.event` 패키지를
`@NamedInterface("events")` 로 공개합니다(그 외 내부 패키지는 비공개).

## 모듈 내부 구조 (엄격한 헥사고날)

```
com.hyk.hexagonal.<module>
├── domain                      # 프레임워크에 의존하지 않는 순수 영역
│   ├── model                   #   도메인 모델 (불변식 강제)
│   ├── event                   #   도메인 이벤트  ← @NamedInterface 로 공개
│   ├── exception
│   └── port
│       ├── in                  #   인바운드 포트(유스케이스 인터페이스 + 커맨드)
│       └── out                 #   아웃바운드 포트(저장/이벤트 발행 인터페이스)
├── application                 # 유스케이스 구현(서비스). 포트에만 의존
└── adapter
    ├── in
    │   ├── web                 #   REST 컨트롤러 + 요청/응답 DTO
    │   └── event               #   다른 모듈 이벤트 구독 리스너
    └── out
        ├── persistence         #   JPA 엔티티 + 매퍼 + 영속성 어댑터
        ├── event               #   ApplicationEventPublisher 기반 이벤트 발행 어댑터
        └── log                 #   (notification) 로그 발송 어댑터
```

핵심 규칙:

- **의존성 역전**: `domain` 은 어떤 인프라(JPA·Spring 이벤트)도 알지 못한다.
  애플리케이션 서비스는 포트(인터페이스)에만 의존하고, 구현은 어댑터에 있다.
- **도메인 모델 ↔ JPA 엔티티 분리**: 별도 클래스이며 `*Mapper` 가 변환한다.
  JPA 엔티티는 도메인 enum 대신 문자열로 상태를 저장해 영속성이 도메인에 의존하지 않게 한다.
- **이벤트 발행도 포트로 추상화**: 서비스는 `OrderEventPublisher` 같은 아웃바운드 포트에
  의존하고, 실제 발행은 `Spring*EventPublisher` 어댑터가 `ApplicationEventPublisher` 로 처리한다.

## 비동기 이벤트 처리

이벤트 리스너는 `@ApplicationModuleListener`(= 비동기 + `REQUIRES_NEW` 트랜잭션 + 발행 기록)를
사용합니다. 발행 모듈의 트랜잭션이 커밋된 뒤 별도 스레드에서 실행되며, 처리 실패 시
이벤트 퍼블리케이션 레지스트리(JPA, `event_publication` 테이블)에 남아 재처리가 가능합니다.
메인 클래스의 `@EnableAsync` 로 비동기를 활성화합니다.

## 실행 방법

JDK 25 가 필요합니다(`JAVA_HOME` 미설정 시 아래처럼 지정).

```powershell
# 1) MySQL 기동 (localhost:3306, root/1234, DB는 자동 생성됨)
# 2) 애플리케이션 실행
$env:JAVA_HOME = "C:\Users\HYK\.jdks\temurin-25.0.3"
.\gradlew.bat bootRun
```

## API 사용 예시

`src/test/http/requests.http` 참고. 요약:

```http
# 1) 재고 입고
POST /api/inventory   { "productId": "P-100", "quantity": 10 }

# 2) 주문 접수 (재고 충분 → 차감 + 알림)
POST /api/orders      { "productId": "P-100", "quantity": 3 }

# 3) 재고 조회 → 7 로 감소 확인
GET  /api/inventory/P-100

# 4) 주문 접수 (재고 부족 → OutOfStock + 알림)
POST /api/orders      { "productId": "P-100", "quantity": 9999 }
```

주문 후 콘솔 로그에서 `[inventory] OrderPlacedEvent 수신 ...`,
`[notification:ORDER] ...`, `[notification:INVENTORY] ...` 메시지로 이벤트 전파를 확인할 수 있습니다.

## 테스트

```powershell
$env:JAVA_HOME = "C:\Users\HYK\.jdks\temurin-25.0.3"

# 모듈 구조 검증 + 단위 테스트 (DB 불필요)
.\gradlew.bat test --tests "com.hyk.hexagonal.ModularityTests" --tests "*InventoryServiceTest"

# 전체 테스트 (MySQL 필요: 통합 테스트 + contextLoads 포함)
.\gradlew.bat test
```

- `ModularityTests`: 순환 의존·NamedInterface 위반을 바이트코드 분석으로 검증하고,
  `build/spring-modulith-docs/` 에 PlantUML/AsciiDoc 모듈 문서를 생성한다(DB 불필요).
- `InventoryServiceTest`: 아웃바운드 포트를 테스트 더블로 대체해 인프라 없이 도메인 로직을 검증한다.
- `OrderModuleIntegrationTests`: `@ApplicationModuleTest` + `Scenario` 로 이벤트 발행을 검증한다(MySQL 필요).
