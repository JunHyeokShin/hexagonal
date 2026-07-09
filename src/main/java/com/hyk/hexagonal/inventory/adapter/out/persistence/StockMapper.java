package com.hyk.hexagonal.inventory.adapter.out.persistence;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import com.hyk.hexagonal.inventory.domain.model.Stock;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class StockMapper {

  static StockJpaEntity toEntity(Stock stock) {
    return StockJpaEntity.builder()
        .id(stock.getId())
        .productId(stock.getProductId())
        .quantity(stock.getQuantity())
        .build();
  }

  static Stock toDomain(StockJpaEntity entity) {
    return Stock.reconstitute(entity.getId(), entity.getProductId(), entity.getQuantity());
  }

}
