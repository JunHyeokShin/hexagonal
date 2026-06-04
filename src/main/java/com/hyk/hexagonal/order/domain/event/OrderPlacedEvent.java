package com.hyk.hexagonal.order.domain.event;

import java.time.Instant;

/**
 * 주문이 접수되었음을 알리는 도메인 이벤트.
 *
 * <p>이 패키지는 {@code @NamedInterface("events")} 로 공개되어 있어
 * inventory / notification 모듈이 이 이벤트 타입에 의존(구독)할 수 있다.
 * 발행 모듈(order)은 구독자의 존재를 알지 못한다.
 */
public record OrderPlacedEvent(Long orderId, String productId, int quantity, Instant placedAt) {}
