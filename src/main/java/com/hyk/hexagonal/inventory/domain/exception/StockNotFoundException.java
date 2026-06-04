package com.hyk.hexagonal.inventory.domain.exception;

/** 상품의 재고가 존재하지 않을 때 발생하는 도메인 예외. */
public class StockNotFoundException extends RuntimeException {

  public StockNotFoundException(String productId) {
    super("재고를 찾을 수 없습니다. productId=" + productId);
  }
}
