package com.pi.deliveryservice.controller;

import com.pi.deliveryservice.dto.AssignCourierRequest;
import com.pi.deliveryservice.dto.DeliveryRequest;
import com.pi.deliveryservice.dto.DeliveryResponse;
import com.pi.deliveryservice.entity.DeliveryStatus;
import com.pi.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService service;

    @PostMapping
    public ResponseEntity<DeliveryResponse> create(@Valid @RequestBody DeliveryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDelivery(request));
    }

    @GetMapping
    public List<DeliveryResponse> getAll(@RequestParam(required = false) DeliveryStatus status) {
        return service.getAll(status);
    }

    @GetMapping("/{id}")
    public DeliveryResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/order/{orderId}")
    public DeliveryResponse getByOrder(@PathVariable Long orderId) {
        return service.getByOrderId(orderId);
    }

    @GetMapping("/courier/{cin}")
    public List<DeliveryResponse> getByCourier(@PathVariable String cin) {
        return service.getByCourier(cin);
    }

    @PutMapping("/{id}/assign")
    public DeliveryResponse assign(@PathVariable Long id, @RequestBody(required = false) AssignCourierRequest body) {
        return service.assignCourier(id, body == null ? null : body.courierCin());
    }

    @PutMapping("/{id}/pickup")
    public DeliveryResponse pickup(@PathVariable Long id) {
        return service.markPickedUp(id);
    }

    @PutMapping("/{id}/deliver")
    public DeliveryResponse deliver(@PathVariable Long id) {
        return service.markDelivered(id);
    }

    @PutMapping("/{id}/cancel")
    public DeliveryResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }
}
