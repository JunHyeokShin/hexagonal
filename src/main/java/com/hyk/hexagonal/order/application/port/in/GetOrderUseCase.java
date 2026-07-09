package com.hyk.hexagonal.order.application.port.in;

import com.hyk.hexagonal.order.domain.model.Order;

public interface GetOrderUseCase {

  Order get(Long id);

}
