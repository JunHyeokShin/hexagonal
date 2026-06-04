package com.hyk.hexagonal.inventory.adapter.in.web;

import com.hyk.hexagonal.inventory.domain.model.Stock;

/** 재고 응답 DTO. */
public record StockResponse(String productId, int quantity) {

  public static StockResponse from(Stock stock) {
    return new StockResponse(stock.getProductId(), stock.getQuantity());
  }
}
