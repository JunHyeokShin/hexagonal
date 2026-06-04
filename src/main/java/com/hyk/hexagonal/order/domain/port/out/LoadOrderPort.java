package com.hyk.hexagonal.order.domain.port.out;

import com.hyk.hexagonal.order.domain.model.Order;
import java.util.Optional;

/** 주문 조회 아웃바운드 포트. */
public interface LoadOrderPort {

  Optional<Order> findById(Long orderId);
}
