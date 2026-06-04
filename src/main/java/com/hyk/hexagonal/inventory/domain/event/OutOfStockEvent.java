package com.hyk.hexagonal.inventory.domain.event;

/** 재고가 부족해 차감에 실패했음을 알리는 도메인 이벤트. */
public record OutOfStockEvent(
    String productId, int requestedQuantity, int availableQuantity, Long orderId) {}
