package com.hyk.hexagonal.inventory.application.port.in;

public record DeductStockCommand(String productId, int quantity, Long orderId) {

}
