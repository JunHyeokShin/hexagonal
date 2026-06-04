package com.hyk.hexagonal.inventory.adapter.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** 재고 Spring Data JPA 리포지토리. */
interface StockJpaRepository extends JpaRepository<StockJpaEntity, Long> {

  Optional<StockJpaEntity> findByProductId(String productId);
}
