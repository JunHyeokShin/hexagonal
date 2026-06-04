/**
 * inventory 모듈이 외부로 공개하는 도메인 이벤트.
 *
 * <p>{@link org.springframework.modulith.NamedInterface} 로 공개하여 notification 모듈이
 * 이 이벤트 타입에만 의존하도록 한다.
 */
@org.springframework.modulith.NamedInterface("events")
package com.hyk.hexagonal.inventory.domain.event;
