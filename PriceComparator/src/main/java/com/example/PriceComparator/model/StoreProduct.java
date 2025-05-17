package com.example.PriceComparator.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "storeProducts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreProduct {
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
