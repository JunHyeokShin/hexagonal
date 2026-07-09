package com.hyk.hexagonal.order.domain.event;

import java.time.Instant;

public record OrderPlacedEvent(
    Long orderId, String productId, int quantity, Instant placedAt
) {

}
