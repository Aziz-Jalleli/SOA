package com.pi.OrderService.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderLineItemDto(
        Long id,
        @Min(value = 1, message = "quantite must be at least 1") int quantite,
        @NotNull(message = "idMenu is required") Long idMenu) {
}