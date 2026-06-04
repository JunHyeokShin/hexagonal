package com.hyk.hexagonal.order.adapter.in.web;

import com.hyk.hexagonal.order.domain.model.Order;
import java.time.Instant;

/** 주문 응답 DTO(웹 어댑터 전용). 도메인 모델을 외부 표현으로 변환한다. */
public record OrderResponse(
    Long orderId, String productId, int quantity, String status, Instant placedAt) {

  public static OrderResponse from(Order order) {
    return new OrderResponse(
        order.getId(),
        order.getProductId(),
        order.getQuantity(),
        order.getStatus().name(),
        order.getPlacedAt());
  }
}
