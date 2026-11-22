package org.pi.kitchenservice.service;

import lombok.RequiredArgsConstructor;
import org.pi.kitchenservice.dto.AcceptTicketRequest;
import org.pi.kitchenservice.dto.TicketRequest;
import org.pi.kitchenservice.dto.TicketResponse;
import org.pi.kitchenservice.exception.InvalidTicketStateException;
import org.pi.kitchenservice.exception.TicketNotFoundException;
import org.pi.kitchenservice.mapper.TicketMapper;
import org.pi.kitchenservice.model.Ticket;
import org.pi.kitchenservice.model.TicketState;
import org.pi.kitchenservice.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;

    @Override
    public TicketResponse createTicket(TicketRequest request) {
        Ticket saved = ticketRepository.save(ticketMapper.toEntity(request));
        return ticketMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TicketResponse getTicket(Long id) {
        return ticketMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll().stream().map(ticketMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByRestaurant(Long restaurantId) {
        return ticketRepository.findByRestaurantId(restaurantId).stream()
                .map(ticketMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponse> getTicketsByState(TicketState state) {
        return ticketRepository.findByState(state).stream()
                .map(ticketMapper::toResponse).toList();
    }

    /** Replaces restaurant and line items; only allowed before preparation starts. */
    @Override
    public TicketResponse reviseTicket(Long id, TicketRequest request) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "revise", TicketState.CREATED, TicketState.ACCEPTED);

        ticket.setRestaurantId(request.restaurantId());
        ticket.clearLineItems();
        request.lineItems().forEach(item -> ticket.addLineItem(ticketMapper.toEntity(item)));
        return ticketMapper.toResponse(ticketRepository.save(ticket));
    }

    @Override
    public TicketResponse acceptTicket(Long id, AcceptTicketRequest request) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "accept", TicketState.CREATED);

        ticket.setState(TicketState.ACCEPTED);
        ticket.setPreparingTime(request.preparingTime());
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse startPreparing(Long id) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "start preparing", TicketState.ACCEPTED);

        ticket.setState(TicketState.PREPARING);
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse markReady(Long id) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "mark as ready", TicketState.PREPARING);

        ticket.setState(TicketState.READY);
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse markPickedUp(Long id) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "mark as picked up", TicketState.READY);

        ticket.setState(TicketState.PICKED_UP);
        ticket.setPickedUpTime(LocalDateTime.now());
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public TicketResponse cancelTicket(Long id) {
        Ticket ticket = findOrThrow(id);
        requireState(ticket, "cancel", TicketState.CREATED, TicketState.ACCEPTED, TicketState.PREPARING);

        ticket.setState(TicketState.CANCELED);
        return ticketMapper.toResponse(ticket);
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.delete(findOrThrow(id));
    }

    private Ticket findOrThrow(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(id));
    }

    private void requireState(Ticket ticket, String action, TicketState... allowed) {
        for (TicketState state : allowed) {
            if (ticket.getState() == state) {
                return;
            }
        }
        throw new InvalidTicketStateException(ticket.getId(), ticket.getState(), action);
    }
}
