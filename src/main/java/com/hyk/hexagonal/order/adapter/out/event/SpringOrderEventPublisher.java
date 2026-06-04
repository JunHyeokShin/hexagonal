package com.hyk.hexagonal.order.adapter.out.event;

import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import com.hyk.hexagonal.order.domain.port.out.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 이벤트 발행 아웃바운드 어댑터.
 *
 * <p>도메인 포트 {@link OrderEventPublisher} 를 Spring 의 {@link ApplicationEventPublisher} 로 구현한다.
 * Spring Modulith 는 이렇게 발행된 이벤트를 모듈 간 통신 및 발행 기록(이벤트 퍼블리케이션)에 활용한다.
 */
@Component
@RequiredArgsConstructor
class SpringOrderEventPublisher implements OrderEventPublisher {

  private final ApplicationEventPublisher delegate;

  @Override
  public void publish(OrderPlacedEvent event) {
    delegate.publishEvent(event);
  }
}
