package com.hyk.hexagonal.notification.adapter.in.event;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/** inventory 모듈의 재고 이벤트를 수신해 알림으로 변환하는 인바운드 이벤트 어댑터. */
@Component
@RequiredArgsConstructor
class InventoryEventListener {

  private final SendNotificationUseCase sendNotificationUseCase;

  @ApplicationModuleListener
  void onDeducted(StockDeductedEvent event) {
    sendNotificationUseCase.send(
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
    sendNotificationUseCase.send(
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
