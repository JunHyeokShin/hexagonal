package com.hyk.hexagonal.inventory.adapter.out.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface StockJpaRepository extends JpaRepository<StockJpaEntity, Long> {

  Optional<StockJpaEntity> findByProductId(String productId);

}
