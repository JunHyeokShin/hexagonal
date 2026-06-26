package com.hyk.hexagonal.inventory.adapter.out.persistence;

import com.hyk.hexagonal.inventory.domain.model.Stock;
import com.hyk.hexagonal.inventory.application.port.out.LoadStockPort;
import com.hyk.hexagonal.inventory.application.port.out.SaveStockPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** 재고 영속성 아웃바운드 어댑터. */
@Component
@RequiredArgsConstructor
class StockPersistenceAdapter implements LoadStockPort, SaveStockPort {

  private final StockJpaRepository repository;

  @Override
  public Optional<Stock> findByProductId(String productId) {
    return repository.findByProductId(productId).map(StockMapper::toDomain);
  }

  @Override
  public Stock save(Stock stock) {
    return StockMapper.toDomain(repository.save(StockMapper.toEntity(stock)));
  }
}
