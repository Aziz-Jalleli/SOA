package com.pi.deliveryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        @NotNull Long orderId,
        Long ticketId,
        @NotNull Long restaurantId,
        Long consumerId,
        @NotBlank String deliveryAddress
) {
}
