package com.hyk.hexagonal.inventory.application;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyk.hexagonal.inventory.application.port.in.DeductStockCommand;
import com.hyk.hexagonal.inventory.application.port.in.DeductStockUseCase;
import com.hyk.hexagonal.inventory.application.port.in.GetStockUseCase;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockCommand;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockUseCase;
import com.hyk.hexagonal.inventory.application.port.out.InventoryEventPublisher;
import com.hyk.hexagonal.inventory.application.port.out.LoadStockPort;
import com.hyk.hexagonal.inventory.application.port.out.SaveStockPort;
import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.inventory.domain.exception.InsufficientStockException;
import com.hyk.hexagonal.inventory.domain.exception.StockNotFoundException;
import com.hyk.hexagonal.inventory.domain.model.Stock;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
class InventoryService implements RegisterStockUseCase, GetStockUseCase, DeductStockUseCase {

  private final SaveStockPort saveStockPort;
  private final LoadStockPort loadStockPort;
  private final InventoryEventPublisher eventPublisher;

  @Override
  @Transactional
  public String register(RegisterStockCommand command) {
    Stock stock = this.loadStockPort.findByProductId(command.productId())
        .map(existing -> {
          existing.increase(command.quantity());
          return existing;
        })
        .orElseGet(() -> Stock.create(command.productId(), command.quantity()));
    Stock saved = this.saveStockPort.save(stock);
    return saved.getProductId();
  }

  @Override
  public Stock get(String productId) {
    return this.loadStockPort.findByProductId(productId)
        .orElseThrow(() -> new StockNotFoundException(productId));
  }

  @Override
  @Transactional
  public void deduct(DeductStockCommand command) {
    Optional<Stock> found = this.loadStockPort.findByProductId(command.productId());
    if (found.isEmpty()) {
      this.eventPublisher.publishOutOfStock(
          new OutOfStockEvent(command.productId(), command.quantity(), 0, command.orderId()));
      return;
    }

    Stock stock = found.get();
    try {
      stock.deduct(command.quantity());
    }
    catch (InsufficientStockException e) {
      this.eventPublisher.publishOutOfStock(
          new OutOfStockEvent(
              command.productId(), command.quantity(), stock.getQuantity(), command.orderId()));
      return;
    }

    Stock saved = this.saveStockPort.save(stock);
    this.eventPublisher.publishDeducted(
        new StockDeductedEvent(
            saved.getProductId(), command.quantity(), saved.getQuantity(), command.orderId()));
  }

}
