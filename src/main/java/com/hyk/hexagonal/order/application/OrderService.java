package com.hyk.hexagonal.order.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.hexagonal.order.application.port.in.GetOrderUseCase;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderCommand;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderUseCase;
import com.hyk.hexagonal.order.application.port.out.LoadOrderPort;
import com.hyk.hexagonal.order.application.port.out.OrderEventPublisher;
import com.hyk.hexagonal.order.application.port.out.SaveOrderPort;
import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import com.hyk.hexagonal.order.domain.exception.OrderNotFoundException;
import com.hyk.hexagonal.order.domain.model.Order;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
class OrderService implements PlaceOrderUseCase, GetOrderUseCase {

  private final SaveOrderPort saveOrderPort;
  private final LoadOrderPort loadOrderPort;
  private final OrderEventPublisher eventPublisher;

  @Override
  @Transactional
  public Long place(PlaceOrderCommand command) {
    Order saved = this.saveOrderPort.save(
        Order.place(command.productId(), command.quantity()));
    this.eventPublisher.publishPlaced(
        new OrderPlacedEvent(
            saved.getId(), saved.getProductId(), saved.getQuantity(), saved.getPlacedAt()));
    return saved.getId();
  }

  @Override
  public Order get(Long id) {
    return this.loadOrderPort.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));
  }

}
