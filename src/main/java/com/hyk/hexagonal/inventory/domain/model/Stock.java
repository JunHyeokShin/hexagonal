package com.hyk.hexagonal.inventory.domain.model;

import com.hyk.hexagonal.inventory.domain.exception.InsufficientStockException;

/**
 * 재고 애그리거트(도메인 모델).
 *
 * <p>특정 상품의 가용 수량을 표현하며 차감/입고 시 불변식을 스스로 강제한다.
 * 가용 수량보다 많이 차감하려 하면 {@link InsufficientStockException} 을 던진다.
 */
public class Stock {

  private final Long id;
  private final String productId;
  private int quantity;

  private Stock(Long id, String productId, int quantity) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
  }

  /** 신규 재고 생성 팩토리. */
  public static Stock create(String productId, int initialQuantity) {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (initialQuantity < 0) {
      throw new IllegalArgumentException("초기 재고는 0 이상이어야 합니다.");
    }
    return new Stock(null, productId, initialQuantity);
  }

  /** 영속 저장소로부터 도메인 객체를 재구성한다. */
  public static Stock reconstitute(Long id, String productId, int quantity) {
    return new Stock(id, productId, quantity);
  }

  /** 재고를 차감한다. 가용 수량이 부족하면 예외를 던진다. */
  public void deduct(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("차감 수량은 1 이상이어야 합니다.");
    }
    if (amount > quantity) {
      throw new InsufficientStockException(productId, quantity, amount);
    }
    this.quantity -= amount;
  }

  /** 재고를 입고(증가)한다. */
  public void increase(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("입고 수량은 1 이상이어야 합니다.");
    }
    this.quantity += amount;
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
}
