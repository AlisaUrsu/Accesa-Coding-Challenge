package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Class representing the alerts set by users to notify them when the price of a product drops below a specified
 * threshold.
 */
@Entity
@Table(name = "price_alert")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private BigDecimal targetPrice;

    private boolean triggered = false;
}
