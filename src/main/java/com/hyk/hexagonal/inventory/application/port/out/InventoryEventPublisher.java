package com.hyk.hexagonal.inventory.application.port.out;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;

/** 재고 도메인 이벤트 발행 아웃바운드 포트. */
public interface InventoryEventPublisher {

  void publishDeducted(StockDeductedEvent event);

  void publishOutOfStock(OutOfStockEvent event);
}
