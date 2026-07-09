package com.hyk.hexagonal.order.adapter.out.persistence;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.order.application.port.out.LoadOrderPort;
import com.hyk.hexagonal.order.application.port.out.SaveOrderPort;
import com.hyk.hexagonal.order.domain.model.Order;

@RequiredArgsConstructor
@Component
class OrderPersistenceAdapter implements SaveOrderPort, LoadOrderPort {

  private final OrderJpaRepository repository;

  @Override
  public Order save(Order order) {
    return OrderMapper.toDomain(this.repository.save(OrderMapper.toEntity(order)));
  }

  @Override
  public Optional<Order> findById(Long id) {
    return this.repository.findById(id).map(OrderMapper::toDomain);
  }

}
