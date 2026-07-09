package com.hyk.hexagonal.order.application.port.out;

import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;

public interface OrderEventPublisher {

  void publishPlaced(OrderPlacedEvent event);

}
