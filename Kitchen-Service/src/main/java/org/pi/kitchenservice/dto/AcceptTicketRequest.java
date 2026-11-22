package org.pi.kitchenservice.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AcceptTicketRequest(
        @NotNull(message = "preparingTime is required") LocalDateTime preparingTime
) {
}
