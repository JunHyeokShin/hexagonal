package com.hyk.hexagonal.inventory.adapter.in.web;

import com.hyk.hexagonal.inventory.domain.exception.StockNotFoundException;
import com.hyk.hexagonal.inventory.application.port.in.GetStockUseCase;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockCommand;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockUseCase;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** 재고 인바운드 웹 어댑터. 입고와 조회를 제공한다. */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final RegisterStockUseCase registerStockUseCase;
  private final GetStockUseCase getStockUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StockResponse register(@RequestBody @Valid RegisterStockRequest request) {
    return StockResponse.from(
        registerStockUseCase.register(
            new RegisterStockCommand(request.productId(), request.quantity())));
  }

  @GetMapping("/{productId}")
  public StockResponse get(@PathVariable String productId) {
    return StockResponse.from(getStockUseCase.getStock(productId));
  }

  @ExceptionHandler(StockNotFoundException.class)
  public ResponseEntity<String> handleNotFound(StockNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
