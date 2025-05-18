package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "discounts_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiscountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    StoreProduct storeProduct;

    LocalDate fromDate;

    LocalDate toDate;

    BigDecimal percentage;

    LocalDate date;
}