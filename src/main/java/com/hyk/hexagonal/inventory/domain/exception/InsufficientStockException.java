package com.hyk.hexagonal.inventory.domain.exception;

/** 가용 재고보다 많은 수량을 차감하려 할 때 발생하는 도메인 예외. */
public class InsufficientStockException extends RuntimeException {

  public InsufficientStockException(String productId, int available, int requested) {
    super(
        "재고가 부족합니다. productId=%s, 가용=%d, 요청=%d".formatted(productId, available, requested));
  }
}
