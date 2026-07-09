package com.hyk.hexagonal.inventory.domain.exception;

public class InsufficientStockException extends RuntimeException {

  public InsufficientStockException(String productId, int available, int requested) {
    super("재고가 부족합니다. productId=%s, 가용=%d, 요청=%d".formatted(productId, available, requested));
  }

}
