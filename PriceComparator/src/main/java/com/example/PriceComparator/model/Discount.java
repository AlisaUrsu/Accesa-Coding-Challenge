package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class for discounts read from input files. This tables stores the StoreProduct that has a discount.
 * Only the most recent discounts (based on import date) are stored in this table.
 */
@Entity
@Table(name = "discounts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StoreProduct storeProduct;

    private LocalDate fromDate;

    private LocalDate toDate;

    private BigDecimal percentage;

    private LocalDate date;
}
