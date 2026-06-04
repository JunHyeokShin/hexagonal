package com.hyk.hexagonal.order.domain.exception;

/** 주문을 찾을 수 없을 때 발생하는 도메인 예외. */
public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(Long orderId) {
    super("주문을 찾을 수 없습니다. id=" + orderId);
  }
}
