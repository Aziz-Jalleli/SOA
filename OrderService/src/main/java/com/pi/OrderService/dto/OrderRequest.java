package com.pi.OrderService.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "consumerId is required") Long consumerId,
        @NotNull(message = "restaurantId is required") Long restaurantId,
        @NotEmpty(message = "an order needs at least one menu") List<@Valid OrderLineItemDto> lineItems,
        @NotNull(message = "deliveryInfo is required") @Valid DeliveryInfoDto deliveryInfo) {
}