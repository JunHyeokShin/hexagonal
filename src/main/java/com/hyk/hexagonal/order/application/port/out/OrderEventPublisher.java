package com.hyk.hexagonal.order.application.port.out;

import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;

/**
 * 주문 도메인 이벤트 발행 아웃바운드 포트.
 *
 * <p>애플리케이션 계층이 Spring 의 {@code ApplicationEventPublisher} 같은 인프라에
 * 직접 의존하지 않도록 발행 행위를 추상화한다. 구현은 어댑터 계층에 있다.
 */
public interface OrderEventPublisher {

  void publish(OrderPlacedEvent event);
}
