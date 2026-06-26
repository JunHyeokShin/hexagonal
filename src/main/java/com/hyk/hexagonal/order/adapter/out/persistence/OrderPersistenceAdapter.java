package com.hyk.hexagonal.order.adapter.out.persistence;

import com.hyk.hexagonal.order.domain.model.Order;
import com.hyk.hexagonal.order.application.port.out.LoadOrderPort;
import com.hyk.hexagonal.order.application.port.out.SaveOrderPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 주문 영속성 아웃바운드 어댑터. 저장/조회 포트를 JPA 리포지토리로 구현한다. */
@Component
@RequiredArgsConstructor
class OrderPersistenceAdapter implements SaveOrderPort, LoadOrderPort {

  private final OrderJpaRepository repository;

  @Override
  public Order save(Order order) {
    OrderJpaEntity saved = repository.save(OrderMapper.toEntity(order));
    return OrderMapper.toDomain(saved);
  }

  @Override
  public Optional<Order> findById(Long orderId) {
    return repository.findById(orderId).map(OrderMapper::toDomain);
  }
}
