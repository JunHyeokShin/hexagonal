package com.hyk.hexagonal.inventory.domain.port.in;

import com.hyk.hexagonal.inventory.domain.model.Stock;

/** 재고 입고(등록) 인바운드 포트. 기존 재고가 있으면 증가, 없으면 새로 생성한다. */
public interface RegisterStockUseCase {

  Stock register(RegisterStockCommand command);
}
