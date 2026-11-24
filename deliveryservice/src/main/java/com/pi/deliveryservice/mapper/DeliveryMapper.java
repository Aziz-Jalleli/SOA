package com.pi.deliveryservice.mapper;

import com.pi.deliveryservice.dto.DeliveryRequest;
import com.pi.deliveryservice.dto.DeliveryResponse;
import com.pi.deliveryservice.entity.Delivery;
import com.pi.deliveryservice.entity.DeliveryStatus;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    public Delivery toEntity(DeliveryRequest r) {
        return Delivery.builder()
                .orderId(r.orderId())
                .ticketId(r.ticketId())
                .restaurantId(r.restaurantId())
                .consumerId(r.consumerId())
                .deliveryAddress(r.deliveryAddress())
                .status(DeliveryStatus.PENDING)
                .build();
    }

    public DeliveryResponse toResponse(Delivery d) {
        return new DeliveryResponse(
                d.getId(), d.getOrderId(), d.getTicketId(), d.getRestaurantId(),
                d.getConsumerId(), d.getDeliveryAddress(), d.getCourierCin(),
                d.getStatus(), d.getCreatedAt(), d.getAssignedAt(),
                d.getPickedUpTime(), d.getDeliveredTime());
    }
}
