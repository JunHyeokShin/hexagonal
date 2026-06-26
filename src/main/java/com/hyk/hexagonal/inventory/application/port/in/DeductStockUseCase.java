package com.hyk.hexagonal.inventory.application.port.in;

/** 재고 차감 인바운드 포트(유스케이스). */
public interface DeductStockUseCase {

  /**
   * 재고를 차감한다. 재고가 없거나 부족하면 예외를 던지지 않고 OutOfStock 이벤트를 발행한다.
   */
  void deduct(DeductStockCommand command);
}
