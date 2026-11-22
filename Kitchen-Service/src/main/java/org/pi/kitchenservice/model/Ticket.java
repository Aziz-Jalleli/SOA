package org.pi.kitchenservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketState state;

    @Column(nullable = false)
    private Long restaurantId;

    /** Time at which the preparation is planned / done. */
    private LocalDateTime preparingTime;

    /** Time at which the order was picked up by the courier. */
    private LocalDateTime pickedUpTime;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<LineItem> lineItems = new ArrayList<>();

    public void addLineItem(LineItem item) {
        lineItems.add(item);
        item.setTicket(this);
    }

    public void clearLineItems() {
        lineItems.forEach(item -> item.setTicket(null));
        lineItems.clear();
    }
}
