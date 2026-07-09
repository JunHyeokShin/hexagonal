package com.hyk.hexagonal.order.application.port.in;

public interface PlaceOrderUseCase {

  Long place(PlaceOrderCommand command);

}
