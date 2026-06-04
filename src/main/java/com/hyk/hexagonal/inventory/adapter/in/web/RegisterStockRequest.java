package com.hyk.hexagonal.inventory.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/** 재고 입고 요청 DTO. */
public record RegisterStockRequest(
    @NotBlank(message = "상품 ID는 필수입니다.") String productId,
    @Positive(message = "입고 수량은 1 이상이어야 합니다.") int quantity) {}
