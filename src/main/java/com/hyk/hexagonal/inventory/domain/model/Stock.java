package com.hyk.hexagonal.inventory.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.hyk.hexagonal.inventory.domain.exception.InsufficientStockException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Stock {

  private final Long id;
  private final String productId;
  private int quantity;

  public static Stock create(String productId, int initialQuantity) {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (initialQuantity < 0) {
      throw new IllegalArgumentException("초기 재고는 0 이상이어야 합니다.");
    }
    return new Stock(null, productId, initialQuantity);
  }

  public static Stock reconstitute(Long id, String productId, int quantity) {
    return new Stock(id, productId, quantity);
  }

  public void deduct(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("차감 수량은 1 이상이어야 합니다.");
    }
    if (amount > this.quantity) {
      throw new InsufficientStockException(this.productId, this.quantity, amount);
    }
    this.quantity -= amount;
  }

  public void increase(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("입고 수량은 1 이상이어야 합니다.");
    }
    this.quantity += amount;
  }

}
