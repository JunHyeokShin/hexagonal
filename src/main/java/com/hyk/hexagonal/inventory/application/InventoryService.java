package com.hyk.hexagonal.inventory.application;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.inventory.domain.exception.InsufficientStockException;
import com.hyk.hexagonal.inventory.domain.exception.StockNotFoundException;
import com.hyk.hexagonal.inventory.domain.model.Stock;
import com.hyk.hexagonal.inventory.domain.port.in.DeductStockCommand;
import com.hyk.hexagonal.inventory.domain.port.in.DeductStockUseCase;
import com.hyk.hexagonal.inventory.domain.port.in.GetStockUseCase;
import com.hyk.hexagonal.inventory.domain.port.in.RegisterStockCommand;
import com.hyk.hexagonal.inventory.domain.port.in.RegisterStockUseCase;
import com.hyk.hexagonal.inventory.domain.port.out.InventoryEventPublisher;
import com.hyk.hexagonal.inventory.domain.port.out.LoadStockPort;
import com.hyk.hexagonal.inventory.domain.port.out.SaveStockPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 재고 유스케이스 구현(애플리케이션 계층).
 *
 * <p>재고 차감은 "예외를 흐름 제어로 쓰지 않고" 결과를 이벤트로 변환한다.
 * 재고가 없거나 부족하면 {@link OutOfStockEvent}, 성공하면 {@link StockDeductedEvent} 를 발행한다.
 */
@Service
@RequiredArgsConstructor
public class InventoryService implements DeductStockUseCase, RegisterStockUseCase, GetStockUseCase {

  private final LoadStockPort loadStockPort;
  private final SaveStockPort saveStockPort;
  private final InventoryEventPublisher eventPublisher;

  @Override
  @Transactional
  public void deduct(DeductStockCommand command) {
    Optional<Stock> found = loadStockPort.findByProductId(command.productId());
    if (found.isEmpty()) {
      eventPublisher.publishOutOfStock(
          new OutOfStockEvent(command.productId(), command.quantity(), 0, command.orderId()));
      return;
    }

    Stock stock = found.get();
    try {
      stock.deduct(command.quantity());
    } catch (InsufficientStockException e) {
      eventPublisher.publishOutOfStock(
          new OutOfStockEvent(
              command.productId(), command.quantity(), stock.getQuantity(), command.orderId()));
      return;
    }

    Stock saved = saveStockPort.save(stock);
    eventPublisher.publishDeducted(
        new StockDeductedEvent(
            saved.getProductId(), command.quantity(), saved.getQuantity(), command.orderId()));
  }

  @Override
  @Transactional
  public Stock register(RegisterStockCommand command) {
    Stock stock =
        loadStockPort
            .findByProductId(command.productId())
            .map(
                existing -> {
                  existing.increase(command.quantity());
                  return existing;
                })
            .orElseGet(() -> Stock.create(command.productId(), command.quantity()));
    return saveStockPort.save(stock);
  }

  @Override
  @Transactional(readOnly = true)
  public Stock getStock(String productId) {
    return loadStockPort
        .findByProductId(productId)
        .orElseThrow(() -> new StockNotFoundException(productId));
  }
}
