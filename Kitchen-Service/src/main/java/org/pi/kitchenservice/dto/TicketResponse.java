package org.pi.kitchenservice.dto;

import org.pi.kitchenservice.model.TicketState;

import java.time.LocalDateTime;
import java.util.List;

public record TicketResponse(
        Long id,
        TicketState state,
        Long restaurantId,
        LocalDateTime preparingTime,
        LocalDateTime pickedUpTime,
        List<LineItemResponse> lineItems
) {
}
