package com.hyk.hexagonal.order.application.port.in;

public record PlaceOrderCommand(String productId, int quantity) {

  public PlaceOrderCommand {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
    }
  }

}
