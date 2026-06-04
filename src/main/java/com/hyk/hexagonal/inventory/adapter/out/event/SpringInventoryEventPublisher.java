package com.hyk.hexagonal.inventory.adapter.out.event;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.inventory.domain.port.out.InventoryEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/** 재고 이벤트 발행 아웃바운드 어댑터. */
@Component
@RequiredArgsConstructor
class SpringInventoryEventPublisher implements InventoryEventPublisher {

  private final ApplicationEventPublisher delegate;

  @Override
  public void publishDeducted(StockDeductedEvent event) {
    delegate.publishEvent(event);
  }

  @Override
  public void publishOutOfStock(OutOfStockEvent event) {
    delegate.publishEvent(event);
  }
}
