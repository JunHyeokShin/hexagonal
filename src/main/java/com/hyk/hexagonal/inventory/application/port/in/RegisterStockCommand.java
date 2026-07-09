package com.hyk.hexagonal.inventory.application.port.in;

public record RegisterStockCommand(String productId, int quantity) {

  public RegisterStockCommand {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("입고 수량은 1 이상이어야 합니다.");
    }
  }

}
