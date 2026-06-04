package com.hyk.hexagonal.member.adapter.out.event;

import com.hyk.hexagonal.member.application.port.out.PublishEventPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringEventPublisherAdapter implements PublishEventPort {

  private final ApplicationEventPublisher publisher;

  SpringEventPublisherAdapter(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void publish(Object event) {
    publisher.publishEvent(event);
  }
}
