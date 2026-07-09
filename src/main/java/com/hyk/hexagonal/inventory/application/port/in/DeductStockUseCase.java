package com.hyk.hexagonal.inventory.application.port.in;

public interface DeductStockUseCase {

  void deduct(DeductStockCommand command);

}
