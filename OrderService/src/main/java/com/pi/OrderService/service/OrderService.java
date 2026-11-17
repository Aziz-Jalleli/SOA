package com.pi.OrderService.service;

import com.pi.OrderService.dto.OrderRequest;
import com.pi.OrderService.dto.OrderResponse;
import com.pi.OrderService.exception.InvalidOrderStateException;
import com.pi.OrderService.exception.OrderNotFoundException;
import com.pi.OrderService.mapper.OrderMapper;
import com.pi.OrderService.model.Order;
import com.pi.OrderService.model.OrderStatus;
import com.pi.OrderService.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository repo;
    private final OrderMapper mapper;

    public OrderResponse createOrder(OrderRequest req) {
        Order order = mapper.toEntity(req);
        order.setStatus(OrderStatus.CREATED);
        return mapper.toResponse(repo.save(order));
    }

    public OrderResponse cancelOrder(Long id) {
        Order order = find(id);
        if (order.getStatus() == OrderStatus.READY || order.getStatus() == OrderStatus.CANCELED) {
            throw new InvalidOrderStateException(
                    "Order " + id + " cannot be canceled in state " + order.getStatus());
        }
        order.setStatus(OrderStatus.CANCELED);
        return mapper.toResponse(repo.save(order));
    }

    public OrderResponse reviseOrder(Long id, OrderRequest req) {
        Order order = find(id);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new InvalidOrderStateException("Only CREATED orders can be revised");
        }
        order.setRestaurantId(req.restaurantId());
        order.setDeliveryInfo(mapper.toEntity(req.deliveryInfo()));
        order.getLineItems().clear();
        order.getLineItems().addAll(mapper.toEntityItems(req.lineItems(), order));
        return mapper.toResponse(repo.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        return mapper.toResponse(find(id));
    }

    private Order find(Long id) {
        return repo.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }
}