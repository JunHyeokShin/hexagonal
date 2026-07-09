package com.hyk.hexagonal.inventory.adapter.out.persistence;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.inventory.application.port.out.LoadStockPort;
import com.hyk.hexagonal.inventory.application.port.out.SaveStockPort;
import com.hyk.hexagonal.inventory.domain.model.Stock;

@RequiredArgsConstructor
@Component
class StockPersistenceAdapter implements SaveStockPort, LoadStockPort {

  private final StockJpaRepository repository;

  @Override
  public Stock save(Stock stock) {
    return StockMapper.toDomain(this.repository.save(StockMapper.toEntity(stock)));
  }

  @Override
  public Optional<Stock> findByProductId(String productId) {
    return this.repository.findByProductId(productId).map(StockMapper::toDomain);
  }

}
