package com.pi.deliveryservice.service;

import com.pi.deliveryservice.dto.DeliveryRequest;
import com.pi.deliveryservice.dto.DeliveryResponse;
import com.pi.deliveryservice.entity.DeliveryStatus;

import java.util.List;

public interface DeliveryService {

    DeliveryResponse createDelivery(DeliveryRequest request);

    DeliveryResponse getById(Long id);

    DeliveryResponse getByOrderId(Long orderId);

    List<DeliveryResponse> getAll(DeliveryStatus status);

    List<DeliveryResponse> getByCourier(String courierCin);

    DeliveryResponse assignCourier(Long id, String courierCin);

    DeliveryResponse markPickedUp(Long id);

    DeliveryResponse markDelivered(Long id);

    DeliveryResponse cancel(Long id);
}
