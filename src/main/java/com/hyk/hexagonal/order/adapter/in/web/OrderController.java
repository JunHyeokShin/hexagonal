package com.hyk.hexagonal.order.adapter.in.web;

import java.net.URI;

import jakarta.validation.Valid;

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

import com.hyk.hexagonal.order.application.port.in.GetOrderUseCase;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderCommand;
import com.hyk.hexagonal.order.application.port.in.PlaceOrderUseCase;
import com.hyk.hexagonal.order.domain.exception.OrderNotFoundException;

@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
class OrderController {

  private final PlaceOrderUseCase placeOrderUseCase;
  private final GetOrderUseCase getOrderUseCase;

  @PostMapping
  ResponseEntity<OrderResponse> place(@RequestBody @Valid PlaceOrderRequest request) {
    Long id = this.placeOrderUseCase.place(
        new PlaceOrderCommand(request.productId(), request.quantity()));
    OrderResponse body = OrderResponse.from(this.getOrderUseCase.get(id));
    return ResponseEntity.created(URI.create("/api/orders/" + id)).body(body);
  }

  @GetMapping("/{id}")
  OrderResponse get(@PathVariable Long id) {
    return OrderResponse.from(this.getOrderUseCase.get(id));
  }

  @ExceptionHandler(OrderNotFoundException.class)
  ResponseEntity<String> handleNotFound(OrderNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

}
