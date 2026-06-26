package com.hyk.hexagonal.inventory.application.port.out;

import com.hyk.hexagonal.inventory.domain.model.Stock;

/** 재고 저장 아웃바운드 포트. */
public interface SaveStockPort {

  Stock save(Stock stock);
}
