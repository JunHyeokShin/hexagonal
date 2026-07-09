package com.hyk.hexagonal.notification.adapter.in.event;

import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;

@RequiredArgsConstructor
@Component
class OrderEventListener {

  private final SendNotificationUseCase sendNotificationUseCase;

  @ApplicationModuleListener
  void on(OrderPlacedEvent event) {
    this.sendNotificationUseCase.send(
        new SendNotificationCommand(
            "ORDER",
            "주문이 접수되었습니다. orderId=%d, 상품=%s, 수량=%d"
                .formatted(event.orderId(), event.productId(), event.quantity())));
  }

}
