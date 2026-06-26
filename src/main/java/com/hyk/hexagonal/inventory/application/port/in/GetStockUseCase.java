package com.hyk.hexagonal.inventory.application.port.in;

import com.hyk.hexagonal.inventory.domain.model.Stock;

/** 재고 조회 인바운드 포트. */
public interface GetStockUseCase {

  /**
   * 상품 재고를 조회한다.
   *
   * @throws com.hyk.hexagonal.inventory.domain.exception.StockNotFoundException 존재하지 않을 때
   */
  Stock getStock(String productId);
}
