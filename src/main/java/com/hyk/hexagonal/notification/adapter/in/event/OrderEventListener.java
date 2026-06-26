package com.hyk.hexagonal.notification.adapter.in.event;

import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/** order 모듈의 주문 이벤트를 수신해 알림으로 변환하는 인바운드 이벤트 어댑터. */
@Component
@RequiredArgsConstructor
class OrderEventListener {

  private final SendNotificationUseCase sendNotificationUseCase;

  @ApplicationModuleListener
  void on(OrderPlacedEvent event) {
    sendNotificationUseCase.send(
        new SendNotificationCommand(
            "ORDER",
            "주문이 접수되었습니다. orderId=%d, 상품=%s, 수량=%d"
                .formatted(event.orderId(), event.productId(), event.quantity())));
  }
}
