package org.pi.kitchenservice.service;

import org.pi.kitchenservice.dto.AcceptTicketRequest;
import org.pi.kitchenservice.dto.TicketRequest;
import org.pi.kitchenservice.dto.TicketResponse;
import org.pi.kitchenservice.model.TicketState;

import java.util.List;

public interface TicketService {

    TicketResponse createTicket(TicketRequest request);

    TicketResponse getTicket(Long id);

    List<TicketResponse> getAllTickets();

    List<TicketResponse> getTicketsByRestaurant(Long restaurantId);

    List<TicketResponse> getTicketsByState(TicketState state);

    TicketResponse reviseTicket(Long id, TicketRequest request);

    TicketResponse acceptTicket(Long id, AcceptTicketRequest request);

    TicketResponse startPreparing(Long id);

    TicketResponse markReady(Long id);

    TicketResponse markPickedUp(Long id);

    TicketResponse cancelTicket(Long id);

    void deleteTicket(Long id);
}
