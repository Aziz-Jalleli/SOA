package com.pi.OrderService.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantite;

    private Long idMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Order order;
}