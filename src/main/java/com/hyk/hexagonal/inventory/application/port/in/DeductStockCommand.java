package com.hyk.hexagonal.inventory.application.port.in;

/** 재고 차감 커맨드. 어떤 주문으로 인한 차감인지 추적하기 위해 orderId 를 포함한다. */
public record DeductStockCommand(String productId, int quantity, Long orderId) {}
