package com.hyk.hexagonal.inventory.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.hyk.hexagonal.inventory.domain.event.OutOfStockEvent;
import com.hyk.hexagonal.inventory.domain.event.StockDeductedEvent;
import com.hyk.hexagonal.inventory.domain.model.Stock;
import com.hyk.hexagonal.inventory.domain.port.in.DeductStockCommand;
import com.hyk.hexagonal.inventory.domain.port.out.InventoryEventPublisher;
import com.hyk.hexagonal.inventory.domain.port.out.LoadStockPort;
import com.hyk.hexagonal.inventory.domain.port.out.SaveStockPort;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * 재고 유스케이스 단위 테스트.
 *
 * <p>아웃바운드 포트를 단순 테스트 더블(fake)로 대체하여 DB·Spring 컨텍스트 없이 도메인 흐름만 검증한다.
 * 포트로 인프라를 격리한 헥사고날 구조의 핵심 이점을 보여준다.
 */
class InventoryServiceTest {

  @Test
  void 재고가_충분하면_차감_후_StockDeductedEvent를_발행한다() {
    FakeStockStore store = new FakeStockStore(Stock.reconstitute(1L, "P-1", 10));
    RecordingEventPublisher publisher = new RecordingEventPublisher();
    InventoryService service = new InventoryService(store, store, publisher);

    service.deduct(new DeductStockCommand("P-1", 3, 100L));

    assertEquals(1, publisher.deducted.size(), "차감 이벤트 1건");
    assertTrue(publisher.outOfStock.isEmpty(), "재고부족 이벤트 없음");
    assertEquals(7, publisher.deducted.get(0).remainingQuantity(), "잔여 수량");
    assertEquals(1, store.saveCount, "변경분 저장됨");
  }

  @Test
  void 재고가_부족하면_저장하지_않고_OutOfStockEvent를_발행한다() {
    FakeStockStore store = new FakeStockStore(Stock.reconstitute(1L, "P-1", 1));
    RecordingEventPublisher publisher = new RecordingEventPublisher();
    InventoryService service = new InventoryService(store, store, publisher);

    service.deduct(new DeductStockCommand("P-1", 5, 100L));

    assertTrue(publisher.deducted.isEmpty(), "차감 이벤트 없음");
    assertEquals(1, publisher.outOfStock.size(), "재고부족 이벤트 1건");
    assertEquals(1, publisher.outOfStock.get(0).availableQuantity(), "가용 수량");
    assertEquals(0, store.saveCount, "저장 호출 없음");
  }

  @Test
  void 재고가_등록되어_있지_않으면_OutOfStockEvent를_발행한다() {
    FakeStockStore store = new FakeStockStore(null);
    RecordingEventPublisher publisher = new RecordingEventPublisher();
    InventoryService service = new InventoryService(store, store, publisher);

    service.deduct(new DeductStockCommand("UNKNOWN", 1, 100L));

    assertEquals(1, publisher.outOfStock.size());
    assertEquals(0, publisher.outOfStock.get(0).availableQuantity());
  }

  /** Load/Save 포트를 함께 구현한 인메모리 테스트 더블. */
  static final class FakeStockStore implements LoadStockPort, SaveStockPort {
    private Stock stock;
    int saveCount = 0;

    FakeStockStore(Stock stock) {
      this.stock = stock;
    }

    @Override
    public Optional<Stock> findByProductId(String productId) {
      return Optional.ofNullable(stock).filter(s -> s.getProductId().equals(productId));
    }

    @Override
    public Stock save(Stock stock) {
      this.stock = stock;
      saveCount++;
      return stock;
    }
  }

  /** 발행된 이벤트를 기록하는 테스트 더블. */
  static final class RecordingEventPublisher implements InventoryEventPublisher {
    final List<StockDeductedEvent> deducted = new ArrayList<>();
    final List<OutOfStockEvent> outOfStock = new ArrayList<>();

    @Override
    public void publishDeducted(StockDeductedEvent event) {
      deducted.add(event);
    }

    @Override
    public void publishOutOfStock(OutOfStockEvent event) {
      outOfStock.add(event);
    }
  }
}
