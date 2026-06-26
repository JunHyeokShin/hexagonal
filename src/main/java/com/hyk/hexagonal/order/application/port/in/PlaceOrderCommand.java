package com.hyk.hexagonal.order.application.port.in;

/**
 * 주문 접수 유스케이스의 입력 모델.
 *
 * <p>웹 DTO와 분리된 애플리케이션 경계 전용 커맨드이며, 생성 시점에 기본 유효성을 보장한다.
 */
public record PlaceOrderCommand(String productId, int quantity) {

  public PlaceOrderCommand {
    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("상품 ID는 필수입니다.");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("주문 수량은 1 이상이어야 합니다.");
    }
  }
}
