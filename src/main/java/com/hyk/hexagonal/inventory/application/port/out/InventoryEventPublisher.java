package com.hyk.hexagonal.inventory.application.port.out;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;

public interface InventoryEventPublisher {

  void publishDeducted(StockDeductedEvent event);

  void publishOutOfStock(OutOfStockEvent event);

}
