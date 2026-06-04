package com.hyk.hexagonal.order.domain.model;

import java.time.Instant;

/**
 * 주문 애그리거트(도메인 모델).
 *
 * <p>프레임워크/영속성 기술에 의존하지 않는 순수 자바 객체이며,
 * 주문 생성 시점의 불변식(상품 ID 필수, 수량 1 이상)을 스스로 강제한다.
 * JPA 엔티티와는 분리되어 있으며 어댑터 계층의 매퍼를 통해 변환된다.
 */
public class Order {

  private final Long id;
  private final String productId;
  private final int quantity;
  private final OrderStatus status;
  private final Instant placedAt;

  private Order(Long id, String productId, int quantity, OrderStatus status, Instant placedAt) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.status = status;
    this.placedAt = placedAt;
  }

  /** 신규 주문 생성 팩토리. 비즈니스 불변식을 여기서 강제한다. */
  public static Order place(String productId, int quantity) {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
    }
    return new Order(null, productId, quantity, OrderStatus.PLACED, Instant.now());
  }

  /** 영속 저장소로부터 도메인 객체를 재구성한다. */
  public static Order reconstitute(
      Long id, String productId, int quantity, OrderStatus status, Instant placedAt) {
    return new Order(id, productId, quantity, status, placedAt);
  }

  public Long getId() {
    return id;
  }

  public String getProductId() {
    return productId;
  }

  public int getQuantity() {
    return quantity;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public Instant getPlacedAt() {
    return placedAt;
  }
}
