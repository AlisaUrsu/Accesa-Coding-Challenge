package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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