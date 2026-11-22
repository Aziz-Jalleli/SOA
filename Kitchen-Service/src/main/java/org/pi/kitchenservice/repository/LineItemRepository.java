package org.pi.kitchenservice.repository;

import org.pi.kitchenservice.model.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {

    List<LineItem> findByTicketId(Long ticketId);
}
