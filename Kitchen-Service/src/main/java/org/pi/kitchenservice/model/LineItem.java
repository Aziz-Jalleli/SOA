package org.pi.kitchenservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "line_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Quantity of the menu to prepare. */
    @Column(nullable = false)
    private Integer quantite;

    /** Id of the menu (owned by RestaurantService). */
    @Column(nullable = false)
    private Long idMenu;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ticket", nullable = false)
    private Ticket ticket;
}
