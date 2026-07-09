package com.hyk.hexagonal.order.application.port.out;

import java.util.Optional;

import com.hyk.hexagonal.order.domain.model.Order;

public interface LoadOrderPort {

  Optional<Order> findById(Long id);

}
