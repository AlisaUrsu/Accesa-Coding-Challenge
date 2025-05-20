package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * This stores every price a product has had. No discounts are applied.
 * The table has a composite key based on store_id, product_id and date (date of the import file)
 */
@Entity
@Table(name = "price_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(PriceHistoryKey.class)
public class PriceHistory {
    @Id
    @ManyToOne
    private Store store;

    @Id
    @ManyToOne
    private Product product;

    @Id
    private LocalDate date;

    private BigDecimal price;

    private String currency;

    private BigDecimal pricePerUnit;

}