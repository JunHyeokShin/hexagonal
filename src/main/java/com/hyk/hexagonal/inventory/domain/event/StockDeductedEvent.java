package com.hyk.hexagonal.inventory.domain.event;

/** 재고 차감이 성공했음을 알리는 도메인 이벤트. */
public record StockDeductedEvent(
    String productId, int deductedQuantity, int remainingQuantity, Long orderId) {}
