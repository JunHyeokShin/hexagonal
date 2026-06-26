package com.hyk.hexagonal.order.application.port.out;

import com.hyk.hexagonal.order.domain.model.Order;

/** 주문 저장 아웃바운드 포트. */
public interface SaveOrderPort {

  /**
   * 주문을 저장하고 식별자가 부여된 주문을 반환한다.
   *
   * @return 저장 후(ID가 채워진) 주문
   */
  Order save(Order order);
}
