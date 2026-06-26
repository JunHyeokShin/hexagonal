package com.hyk.hexagonal.order.application;

import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import com.hyk.hexagonal.order.domain.exception.OrderNotFoundException;
import com.hyk.hexagonal.order.domain.model.Order;
import com.hyk.hexagonal.order.application.port.in.GetOrderUseCase;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderCommand;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderUseCase;
import com.hyk.hexagonal.order.application.port.out.LoadOrderPort;
import com.hyk.hexagonal.order.application.port.out.OrderEventPublisher;
import com.hyk.hexagonal.order.application.port.out.SaveOrderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 주문 유스케이스 구현(애플리케이션 계층).
 *
 * <p>도메인 포트(in/out)에만 의존하며, 구체 인프라(JPA·Spring 이벤트)는 알지 못한다.
 * 주문 저장과 이벤트 발행을 하나의 트랜잭션으로 묶어 일관성을 보장한다.
 */
@Service
@RequiredArgsConstructor
public class OrderService implements PlaceOrderUseCase, GetOrderUseCase {

  private final SaveOrderPort saveOrderPort;
  private final LoadOrderPort loadOrderPort;
  private final OrderEventPublisher eventPublisher;

  @Override
  @Transactional
  public Long placeOrder(PlaceOrderCommand command) {
    Order saved = saveOrderPort.save(Order.place(command.productId(), command.quantity()));
    eventPublisher.publish(
        new OrderPlacedEvent(
            saved.getId(), saved.getProductId(), saved.getQuantity(), saved.getPlacedAt()));
    return saved.getId();
  }

  @Override
  @Transactional(readOnly = true)
  public Order getOrder(Long orderId) {
    return loadOrderPort.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
  }
}
