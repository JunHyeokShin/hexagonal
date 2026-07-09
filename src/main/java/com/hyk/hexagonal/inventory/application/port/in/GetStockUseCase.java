package com.hyk.hexagonal.inventory.application.port.in;

import com.hyk.hexagonal.inventory.domain.model.Stock;

public interface GetStockUseCase {

  Stock get(String productId);

}
