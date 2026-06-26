package com.hyk.hexagonal.order.adapter.in.web;

import com.hyk.hexagonal.order.domain.exception.OrderNotFoundException;
import com.hyk.hexagonal.order.application.port.in.GetOrderUseCase;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderCommand;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderUseCase;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 주문 인바운드 웹 어댑터.
 *
 * <p>인바운드 포트(유스케이스)에만 의존하며, 요청/응답 DTO와 도메인 사이를 변환한다.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

  private final PlaceOrderUseCase placeOrderUseCase;
  private final GetOrderUseCase getOrderUseCase;

  @PostMapping
  public ResponseEntity<OrderResponse> place(@RequestBody @Valid PlaceOrderRequest request) {
    Long orderId =
        placeOrderUseCase.placeOrder(new PlaceOrderCommand(request.productId(), request.quantity()));
    OrderResponse body = OrderResponse.from(getOrderUseCase.getOrder(orderId));
    return ResponseEntity.created(URI.create("/api/orders/" + orderId)).body(body);
  }

  @GetMapping("/{orderId}")
  public OrderResponse get(@PathVariable Long orderId) {
    return OrderResponse.from(getOrderUseCase.getOrder(orderId));
  }

  @ExceptionHandler(OrderNotFoundException.class)
  public ResponseEntity<String> handleNotFound(OrderNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
