package com.hyk.hexagonal.inventory.adapter.out.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.inventory.application.port.out.InventoryEventPublisher;
import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;

@RequiredArgsConstructor
@Component
class SpringInventoryEventPublisher implements InventoryEventPublisher {

  private final ApplicationEventPublisher delegate;

  @Override
  public void publishDeducted(StockDeductedEvent event) {
    this.delegate.publishEvent(event);
  }

  @Override
  public void publishOutOfStock(OutOfStockEvent event) {
    this.delegate.publishEvent(event);
  }

}
