package org.pi.kitchenservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pi.kitchenservice.dto.AcceptTicketRequest;
import org.pi.kitchenservice.dto.TicketRequest;
import org.pi.kitchenservice.dto.TicketResponse;
import org.pi.kitchenservice.model.TicketState;
import org.pi.kitchenservice.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ResponseEntity<TicketResponse> create(@Valid @RequestBody TicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.createTicket(request));
    }

    @GetMapping
    public List<TicketResponse> getAll(@RequestParam(required = false) TicketState state) {
        return state == null ? ticketService.getAllTickets() : ticketService.getTicketsByState(state);
    }

    @GetMapping("/{id}")
    public TicketResponse getById(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public List<TicketResponse> getByRestaurant(@PathVariable Long restaurantId) {
        return ticketService.getTicketsByRestaurant(restaurantId);
    }

    @PutMapping("/{id}")
    public TicketResponse revise(@PathVariable Long id, @Valid @RequestBody TicketRequest request) {
        return ticketService.reviseTicket(id, request);
    }

    @PatchMapping("/{id}/accept")
    public TicketResponse accept(@PathVariable Long id, @Valid @RequestBody AcceptTicketRequest request) {
        return ticketService.acceptTicket(id, request);
    }

    @PatchMapping("/{id}/preparing")
    public TicketResponse startPreparing(@PathVariable Long id) {
        return ticketService.startPreparing(id);
    }

    @PatchMapping("/{id}/ready")
    public TicketResponse markReady(@PathVariable Long id) {
        return ticketService.markReady(id);
    }

    @PatchMapping("/{id}/picked-up")
    public TicketResponse markPickedUp(@PathVariable Long id) {
        return ticketService.markPickedUp(id);
    }

    @PatchMapping("/{id}/cancel")
    public TicketResponse cancel(@PathVariable Long id) {
        return ticketService.cancelTicket(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
