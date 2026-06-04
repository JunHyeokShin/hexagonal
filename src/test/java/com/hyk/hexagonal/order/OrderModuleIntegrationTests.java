package com.hyk.hexagonal.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.hyk.hexagonal.order.domain.event.OrderPlacedEvent;
import com.hyk.hexagonal.order.domain.port.in.PlaceOrderCommand;
import com.hyk.hexagonal.order.domain.port.in.PlaceOrderUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.Scenario;

/**
 * order 모듈 통합 테스트.
 *
 * <p>{@code @ApplicationModuleTest} 는 order 모듈만 부트스트랩한다. {@link Scenario} API 로
 * "주문 접수"라는 자극(stimulus)을 주고 {@link OrderPlacedEvent} 가 발행되는지 검증한다.
 *
 * <p><b>실행 전제:</b> application.yaml 의 MySQL(localhost:3306/hexagonal)이 구동 중이어야 한다.
 */
@ApplicationModuleTest
class OrderModuleIntegrationTests {

  @Autowired PlaceOrderUseCase placeOrderUseCase;

  @Test
  void 주문을_접수하면_OrderPlacedEvent가_발행된다(Scenario scenario) {
    scenario
        .stimulate(() -> placeOrderUseCase.placeOrder(new PlaceOrderCommand("P-100", 2)))
        .andWaitForEventOfType(OrderPlacedEvent.class)
        .matching(event -> "P-100".equals(event.productId()))
        .toArriveAndVerify(event -> assertEquals(2, event.quantity()));
  }
}
