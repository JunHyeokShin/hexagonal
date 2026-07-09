package com.hyk.hexagonal.inventory.adapter.in.web;

import com.hyk.hexagonal.inventory.domain.model.Stock;

record StockResponse(String productId, int quantity) {

  static StockResponse from(Stock stock) {
    return new StockResponse(stock.getProductId(), stock.getQuantity());
  }

}
