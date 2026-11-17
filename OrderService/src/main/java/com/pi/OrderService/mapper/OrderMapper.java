package com.pi.OrderService.mapper;


import com.pi.OrderService.dto.DeliveryInfoDto;
import com.pi.OrderService.dto.OrderLineItemDto;
import com.pi.OrderService.dto.OrderRequest;
import com.pi.OrderService.dto.OrderResponse;
import com.pi.OrderService.model.DeliveryInfo;
import com.pi.OrderService.model.Order;
import com.pi.OrderService.model.OrderLineItems;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest req) {
        Order order = new Order();
        order.setConsumerId(req.consumerId());
        order.setRestaurantId(req.restaurantId());
        order.setDeliveryInfo(toEntity(req.deliveryInfo()));
        order.setLineItems(toEntityItems(req.lineItems(), order));
        return order;
    }

    public List<OrderLineItems> toEntityItems(List<OrderLineItemDto> dtos, Order order) {
        return dtos.stream().map(d -> {
            OrderLineItems li = new OrderLineItems();
            li.setQuantite(d.quantite());
            li.setIdMenu(d.idMenu());
            li.setOrder(order);
            return li;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    public DeliveryInfo toEntity(DeliveryInfoDto dto) {
        return new DeliveryInfo(dto.deliveryTime(), dto.deliveryAdress());
    }

    public OrderResponse toResponse(Order o) {
        return new OrderResponse(
                o.getId(),
                o.getStatus(),
                o.getConsumerId(),
                o.getRestaurantId(),
                o.getLineItems().stream()
                        .map(li -> new OrderLineItemDto(li.getId(), li.getQuantite(), li.getIdMenu()))
                        .toList(),
                new DeliveryInfoDto(o.getDeliveryInfo().getDeliveryTime(),
                        o.getDeliveryInfo().getDeliveryAdress()));
    }
}