package org.pi.kitchenservice.repository;

import org.pi.kitchenservice.model.Ticket;
import org.pi.kitchenservice.model.TicketState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByRestaurantId(Long restaurantId);

    List<Ticket> findByState(TicketState state);
}
