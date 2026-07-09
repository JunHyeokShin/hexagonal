package com.hyk.hexagonal.inventory.adapter.in.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.inventory.application.port.in.DeductStockCommand;
import com.hyk.hexagonal.inventory.application.port.in.DeductStockUseCase;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderPlacedEventListener {

  private final DeductStockUseCase deductStockUseCase;

  @ApplicationModuleListener
  void on(OrderPlacedEvent event) {
    log.info("[inventory] OrderPlacedEvent 수신 -> 재고 차감 시도 orderId={}, productId={}, quantity={}",
        event.orderId(), event.productId(), event.quantity());
    this.deductStockUseCase.deduct(
        new DeductStockCommand(event.productId(), event.quantity(), event.orderId()));
  }

}
