package com.hyk.hexagonal.inventory.domain.event;

public record StockDeductedEvent(
    String productId, int deductedQuantity, int remainingQuantity, Long orderId
) {

}
