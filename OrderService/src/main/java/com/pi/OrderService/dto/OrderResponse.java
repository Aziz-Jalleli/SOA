package com.pi.OrderService.dto;

import com.pi.OrderService.model.OrderStatus;

import java.util.List;

public record OrderResponse(
        Long id,
        OrderStatus status,
        Long consumerId,
        Long restaurantId,
        List<OrderLineItemDto> lineItems,
        DeliveryInfoDto deliveryInfo) {
}