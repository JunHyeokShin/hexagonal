package com.hyk.hexagonal.inventory.application.port.out;

import com.hyk.hexagonal.inventory.domain.model.Stock;

public interface SaveStockPort {

  Stock save(Stock stock);

}
