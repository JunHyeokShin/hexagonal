package com.hyk.hexagonal.inventory.adapter.out.persistence;

import com.hyk.hexagonal.inventory.domain.model.Stock;

/** 도메인 모델 ↔ JPA 엔티티 변환기. */
final class StockMapper {

  private StockMapper() {}

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
