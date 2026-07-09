package com.hyk.hexagonal.order.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.hyk.hexagonal.order.domain.model.Order;
import com.hyk.hexagonal.order.domain.model.OrderStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class OrderMapper {

  static OrderJpaEntity toEntity(Order order) {
    return OrderJpaEntity.builder()
        .id(order.getId())
        .productId(order.getProductId())
        .quantity(order.getQuantity())
        .status(order.getStatus().name())
        .placedAt(order.getPlacedAt())
        .build();
  }

  static Order toDomain(OrderJpaEntity entity) {
    return Order.reconstitute(
        entity.getId(),
        entity.getProductId(),
        entity.getQuantity(),
        OrderStatus.valueOf(entity.getStatus()),
        entity.getPlacedAt()
    );
  }

}
