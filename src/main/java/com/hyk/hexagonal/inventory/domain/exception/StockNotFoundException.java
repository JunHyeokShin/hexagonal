package com.hyk.hexagonal.inventory.domain.exception;

public class StockNotFoundException extends RuntimeException {

  public StockNotFoundException(String productId) {
    super("재고를 찾을 수 없습니다. productId=" + productId);
  }

}
