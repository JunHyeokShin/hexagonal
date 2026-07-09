package com.hyk.hexagonal.order.adapter.out.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.order.application.port.out.OrderEventPublisher;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;

@RequiredArgsConstructor
@Component
class SpringOrderEventPublisher implements OrderEventPublisher {

  private final ApplicationEventPublisher delegate;

  @Override
  public void publishPlaced(OrderPlacedEvent event) {
    this.delegate.publishEvent(event);
  }

}
