package com.pi.OrderService.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record DeliveryInfoDto(
        @Min(value = 1, message = "deliveryTime must be positive") int deliveryTime,
        @NotBlank(message = "deliveryAdress is required") String deliveryAdress) {
}