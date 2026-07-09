package com.hyk.hexagonal.inventory.adapter.in.web;

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

import com.hyk.hexagonal.inventory.application.port.in.GetStockUseCase;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockCommand;
import com.hyk.hexagonal.inventory.application.port.in.RegisterStockUseCase;
import com.hyk.hexagonal.inventory.domain.exception.StockNotFoundException;

@RequiredArgsConstructor
@RequestMapping("/api/inventory")
@RestController
class InventoryController {

  private final RegisterStockUseCase registerStockUseCase;
  private final GetStockUseCase getStockUseCase;

  @PostMapping
  ResponseEntity<StockResponse> register(@RequestBody @Valid RegisterStockRequest request) {
    String productId = this.registerStockUseCase.register(
        new RegisterStockCommand(request.productId(), request.quantity()));
    StockResponse body = StockResponse.from(this.getStockUseCase.get(productId));
    return ResponseEntity.created(URI.create("/api/inventory/" + productId)).body(body);
  }

  @GetMapping("/{productId}")
  StockResponse get(@PathVariable String productId) {
    return StockResponse.from(this.getStockUseCase.get(productId));
  }

  @ExceptionHandler(StockNotFoundException.class)
  ResponseEntity<String> handleNotFound(StockNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

}
