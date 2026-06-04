package com.hyk.hexagonal.order.domain.port.in;

/** 주문 접수 인바운드 포트(유스케이스). */
public interface PlaceOrderUseCase {

  /**
   * 주문을 접수하고 생성된 주문 식별자를 반환한다.
   *
   * @return 생성된 주문 ID
   */
  Long placeOrder(PlaceOrderCommand command);
}
