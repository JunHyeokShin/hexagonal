package com.hyk.hexagonal.order.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/** 주문 접수 요청 DTO(웹 어댑터 전용). */
public record PlaceOrderRequest(
    @NotBlank(message = "상품 ID는 필수입니다.") String productId,
    @Positive(message = "주문 수량은 1 이상이어야 합니다.") int quantity) {}
