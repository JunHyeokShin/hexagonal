package com.hyk.hexagonal.inventory.adapter.in.event;

import com.hyk.hexagonal.inventory.domain.port.in.DeductStockCommand;
import com.hyk.hexagonal.inventory.domain.port.in.DeductStockUseCase;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * order 모듈의 {@link OrderPlacedEvent} 를 수신하는 인바운드 이벤트 어댑터.
 *
 * <p>{@code @ApplicationModuleListener} 는 비동기 + 별도 트랜잭션(REQUIRES_NEW) + 발행 기록을 결합한다.
 * 즉 주문 트랜잭션이 커밋된 뒤에 실행되며, 처리 실패 시 이벤트 퍼블리케이션 레지스트리에 남아 재시도가 가능하다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
class OrderPlacedEventListener {

  private final DeductStockUseCase deductStockUseCase;

  @ApplicationModuleListener
  void on(OrderPlacedEvent event) {
    log.info(
        "[inventory] OrderPlacedEvent 수신 → 재고 차감 시도 orderId={}, productId={}, quantity={}",
        event.orderId(),
        event.productId(),
        event.quantity());
    deductStockUseCase.deduct(
        new DeductStockCommand(event.productId(), event.quantity(), event.orderId()));
  }
}
