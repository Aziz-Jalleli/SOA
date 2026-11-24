package com.pi.deliveryservice.dto;

import com.pi.deliveryservice.entity.DeliveryStatus;

import java.time.LocalDateTime;

public record DeliveryResponse(
        Long id,
        Long orderId,
        Long ticketId,
        Long restaurantId,
        Long consumerId,
        String deliveryAddress,
        String courierCin,
        DeliveryStatus status,
        LocalDateTime createdAt,
        LocalDateTime assignedAt,
        LocalDateTime pickedUpTime,
        LocalDateTime deliveredTime
) {
}
