package com.pi.deliveryservice.service.impl;

import com.pi.deliveryservice.client.CourtierClient;
import com.pi.deliveryservice.dto.CourtierDto;
import com.pi.deliveryservice.dto.DeliveryRequest;
import com.pi.deliveryservice.dto.DeliveryResponse;
import com.pi.deliveryservice.entity.Delivery;
import com.pi.deliveryservice.entity.DeliveryStatus;
import com.pi.deliveryservice.exception.BusinessException;
import com.pi.deliveryservice.exception.ResourceNotFoundException;
import com.pi.deliveryservice.mapper.DeliveryMapper;
import com.pi.deliveryservice.repository.DeliveryRepository;
import com.pi.deliveryservice.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository repository;
    private final DeliveryMapper mapper;
    private final CourtierClient courtierClient;

    @Override
    public DeliveryResponse createDelivery(DeliveryRequest request) {
        if (repository.existsByOrderId(request.orderId())) {
            throw new BusinessException("A delivery already exists for order " + request.orderId());
        }
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryResponse getById(Long id) {
        return mapper.toResponse(find(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryResponse getByOrderId(Long orderId) {
        return repository.findByOrderId(orderId)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("No delivery for order " + orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryResponse> getAll(DeliveryStatus status) {
        List<Delivery> list = (status == null) ? repository.findAll() : repository.findByStatus(status);
        return list.stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryResponse> getByCourier(String courierCin) {
        return repository.findByCourierCin(courierCin).stream().map(mapper::toResponse).toList();
    }

    @Override
    public DeliveryResponse assignCourier(Long id, String courierCin) {
        Delivery d = find(id);
        if (d.getStatus() != DeliveryStatus.PENDING) {
            throw new BusinessException("Only a PENDING delivery can be assigned (current: " + d.getStatus() + ")");
        }
        boolean auto = courierCin == null || courierCin.isBlank();
        if (courtierClient.isEnabled()) {
            if (auto) {
                courierCin = courtierClient.getAvailableCourtiers().stream()
                        .findFirst()
                        .map(CourtierDto::cin)
                        .orElseThrow(() -> new BusinessException("No courier is currently available"));
            } else {
                CourtierDto courier = courtierClient.getCourtier(courierCin);
                if (!Boolean.TRUE.equals(courier.disponible())) {
                    throw new BusinessException("Courier " + courierCin + " is not available");
                }
            }
            courtierClient.setDisponible(courierCin, false);
        } else if (auto) {
            throw new BusinessException("courierCin is required when courtier integration is disabled");
        }
        d.setCourierCin(courierCin);
        d.setStatus(DeliveryStatus.ASSIGNED);
        d.setAssignedAt(LocalDateTime.now());
        return mapper.toResponse(repository.save(d));
    }

    @Override
    public DeliveryResponse markPickedUp(Long id) {
        Delivery d = find(id);
        if (d.getStatus() != DeliveryStatus.ASSIGNED) {
            throw new BusinessException("Delivery must be ASSIGNED to be picked up (current: " + d.getStatus() + ")");
        }
        d.setStatus(DeliveryStatus.PICKED_UP);
        d.setPickedUpTime(LocalDateTime.now());
        return mapper.toResponse(repository.save(d));
    }

    @Override
    public DeliveryResponse markDelivered(Long id) {
        Delivery d = find(id);
        if (d.getStatus() != DeliveryStatus.PICKED_UP) {
            throw new BusinessException("Delivery must be PICKED_UP to be delivered (current: " + d.getStatus() + ")");
        }
        d.setStatus(DeliveryStatus.DELIVERED);
        d.setDeliveredTime(LocalDateTime.now());
        releaseCourier(d);
        return mapper.toResponse(repository.save(d));
    }

    @Override
    public DeliveryResponse cancel(Long id) {
        Delivery d = find(id);
        if (d.getStatus() == DeliveryStatus.DELIVERED || d.getStatus() == DeliveryStatus.CANCELED) {
            throw new BusinessException("Cannot cancel a delivery that is " + d.getStatus());
        }
        d.setStatus(DeliveryStatus.CANCELED);
        releaseCourier(d);
        return mapper.toResponse(repository.save(d));
    }

    private void releaseCourier(Delivery d) {
        if (d.getCourierCin() != null && courtierClient.isEnabled()) {
            courtierClient.setDisponible(d.getCourierCin(), true);
        }
    }

    private Delivery find(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found: " + id));
    }
}
