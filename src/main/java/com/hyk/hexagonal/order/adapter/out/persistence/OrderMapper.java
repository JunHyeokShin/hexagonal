package com.hyk.hexagonal.order.adapter.out.persistence;

import com.hyk.hexagonal.order.domain.model.Order;
import com.hyk.hexagonal.order.domain.model.OrderStatus;

/** 도메인 모델 ↔ JPA 엔티티 변환기. */
final class OrderMapper {

  private OrderMapper() {}

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
        entity.getPlacedAt());
  }
}
