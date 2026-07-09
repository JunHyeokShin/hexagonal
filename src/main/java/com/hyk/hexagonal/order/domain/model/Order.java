package com.hyk.hexagonal.order.domain.model;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {

  private final Long id;
  private final String productId;
  private final int quantity;
  private final OrderStatus status;
  private final Instant placedAt;

  public static Order place(String productId, int quantity) {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
    }
    return new Order(null, productId, quantity, OrderStatus.PLACED, Instant.now());
  }

  public static Order reconstitute(
      Long id, String productId, int quantity, OrderStatus status, Instant placedAt) {
    return new Order(id, productId, quantity, status, placedAt);
  }

}
