package com.hyk.hexagonal.order.adapter.in.web;

import java.time.Instant;

import com.hyk.hexagonal.order.domain.model.Order;

record OrderResponse(
    Long id, String productId, int quantity, String status, Instant placedAt
) {

  static OrderResponse from(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getProductId(),
        order.getQuantity(),
        order.getStatus().name(),
        order.getPlacedAt()
    );
  }

}
