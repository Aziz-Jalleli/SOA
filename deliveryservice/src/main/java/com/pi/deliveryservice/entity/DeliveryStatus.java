package com.pi.deliveryservice.entity;

public enum DeliveryStatus {
    PENDING,     // created, waiting for a courier
    ASSIGNED,    // a courier has been assigned
    PICKED_UP,   // courier picked the order up at the restaurant
    DELIVERED,   // delivered to the customer
    CANCELED
}
