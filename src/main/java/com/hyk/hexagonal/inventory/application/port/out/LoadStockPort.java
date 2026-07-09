package com.hyk.hexagonal.inventory.application.port.out;

import java.util.Optional;

import com.hyk.hexagonal.inventory.domain.model.Stock;

public interface LoadStockPort {

  Optional<Stock> findByProductId(String productId);

}
