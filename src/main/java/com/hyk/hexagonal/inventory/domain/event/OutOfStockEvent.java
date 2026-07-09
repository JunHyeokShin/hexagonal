package com.hyk.hexagonal.inventory.domain.event;

public record OutOfStockEvent(
    String productId, int requestedQuantity, int availableQuantity, Long orderId
) {

}
