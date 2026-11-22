package org.pi.kitchenservice.mapper;

import org.pi.kitchenservice.dto.LineItemRequest;
import org.pi.kitchenservice.dto.LineItemResponse;
import org.pi.kitchenservice.dto.TicketRequest;
import org.pi.kitchenservice.dto.TicketResponse;
import org.pi.kitchenservice.model.LineItem;
import org.pi.kitchenservice.model.Ticket;
import org.pi.kitchenservice.model.TicketState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketRequest request) {
        Ticket ticket = Ticket.builder()
                .restaurantId(request.restaurantId())
                .state(TicketState.CREATED)
                .build();
        request.lineItems().forEach(item -> ticket.addLineItem(toEntity(item)));
        return ticket;
    }

    public LineItem toEntity(LineItemRequest request) {
        return LineItem.builder()
                .idMenu(request.idMenu())
                .quantite(request.quantite())
                .build();
    }

    public TicketResponse toResponse(Ticket ticket) {
        List<LineItemResponse> items = ticket.getLineItems().stream()
                .map(this::toResponse)
                .toList();
        return new TicketResponse(
                ticket.getId(),
                ticket.getState(),
                ticket.getRestaurantId(),
                ticket.getPreparingTime(),
                ticket.getPickedUpTime(),
                items);
    }

    public LineItemResponse toResponse(LineItem item) {
        return new LineItemResponse(item.getId(), item.getIdMenu(), item.getQuantite());
    }
}
