package com.hyk.hexagonal.notification.adapter.in.event;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;

@RequiredArgsConstructor
@Component
class InventoryEventListener {

  private final SendNotificationUseCase sendNotificationUseCase;

  @ApplicationModuleListener
  void onDeducted(StockDeductedEvent event) {
    this.sendNotificationUseCase.send(
        new SendNotificationCommand(
            "INVENTORY",
            "재고가 차감되었습니다. orderId=%d, 상품=%s, 차감=%d, 잔여=%d"
                .formatted(
                    event.orderId(),
                    event.productId(),
                    event.deductedQuantity(),
                    event.remainingQuantity())));
  }

  @ApplicationModuleListener
  void onOutOfStock(OutOfStockEvent event) {
    this.sendNotificationUseCase.send(
        new SendNotificationCommand(
            "INVENTORY",
            "재고가 부족하여 처리되지 못했습니다. orderId=%d, 상품=%s, 요청=%d, 가용=%d"
                .formatted(
                    event.orderId(),
                    event.productId(),
                    event.requestedQuantity(),
                    event.availableQuantity())));
  }

}
