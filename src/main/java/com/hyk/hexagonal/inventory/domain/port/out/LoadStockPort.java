package com.hyk.hexagonal.inventory.domain.port.out;

import com.hyk.hexagonal.inventory.domain.model.Stock;
import java.util.Optional;

/** 재고 조회 아웃바운드 포트. */
public interface LoadStockPort {

  Optional<Stock> findByProductId(String productId);
}
