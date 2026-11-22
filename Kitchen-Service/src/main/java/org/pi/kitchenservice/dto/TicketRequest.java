package org.pi.kitchenservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TicketRequest(
        @NotNull(message = "restaurantId is required") Long restaurantId,
        @NotEmpty(message = "a ticket needs at least one line item") @Valid List<LineItemRequest> lineItems
) {
}
