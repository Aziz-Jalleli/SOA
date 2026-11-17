package com.pi.OrderService.controller;
import com.pi.OrderService.dto.OrderRequest;
import com.pi.OrderService.dto.OrderResponse;
import com.pi.OrderService.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(req));
    }

    @GetMapping("/{id}")
    public OrderResponse getOrder(@PathVariable Long id) {
        return service.getOrder(id);
    }

    @PutMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id) {
        return service.cancelOrder(id);
    }

    @PutMapping("/{id}")
    public OrderResponse reviseOrder(@PathVariable Long id, @Valid @RequestBody OrderRequest req) {
        return service.reviseOrder(id, req);
    }
}