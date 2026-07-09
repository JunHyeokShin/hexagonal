package com.hyk.hexagonal.order.application.port.out;

import com.hyk.hexagonal.order.domain.model.Order;

public interface SaveOrderPort {

  Order save(Order order);

}
