package com.hyk.hexagonal.order.domain.port.in;

import com.hyk.hexagonal.order.domain.model.Order;

/** 주문 조회 인바운드 포트(유스케이스). */
public interface GetOrderUseCase {

  /**
   * 주문을 조회한다.
   *
   * @throws com.hyk.hexagonal.order.domain.exception.OrderNotFoundException 존재하지 않을 때
   */
  Order getOrder(Long orderId);
}
