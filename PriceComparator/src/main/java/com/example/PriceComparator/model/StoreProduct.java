package com.example.PriceComparator.model;

import jakarta.persistence.*;
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
@IdClass(StoreProductKey.class)
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
