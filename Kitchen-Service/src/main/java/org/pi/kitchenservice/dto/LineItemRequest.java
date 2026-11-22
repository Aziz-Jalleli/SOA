package org.pi.kitchenservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record LineItemRequest(
        @NotNull(message = "idMenu is required") Long idMenu,
        @NotNull(message = "quantite is required") @Min(value = 1, message = "quantite must be at least 1") Integer quantite
) {
}
