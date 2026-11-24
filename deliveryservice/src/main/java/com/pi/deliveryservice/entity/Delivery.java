package com.pi.deliveryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Order handled by OrderService. */
    @Column(nullable = false, unique = true)
    private Long orderId;

    /** Kitchen ticket (KitchenService), optional. */
    private Long ticketId;

    @Column(nullable = false)
    private Long restaurantId;

    /** Customer id (OrderService.consumerId). */
    private Long consumerId;

    @Column(nullable = false)
    private String deliveryAddress;

    /** CIN of the assigned courier (CourtierService), null until assigned. */
    private String courierCin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime assignedAt;
    private LocalDateTime pickedUpTime;
    private LocalDateTime deliveredTime;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = DeliveryStatus.PENDING;
    }
}
