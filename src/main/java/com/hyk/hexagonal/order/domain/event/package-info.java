/**
 * order 모듈이 외부로 공개하는 도메인 이벤트.
 *
 * <p>{@link org.springframework.modulith.NamedInterface} 로 명시적으로 공개하여
 * 다른 모듈이 이 이벤트 타입에만 의존하도록 모듈 경계를 좁힌다.
 */
@org.springframework.modulith.NamedInterface("events")
package com.hyk.hexagonal.order.domain.event;
