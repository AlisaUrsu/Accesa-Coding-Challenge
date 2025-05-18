package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    Long id;

    @ManyToOne
    StoreProduct storeProduct;

    LocalDate fromDate;

    LocalDate toDate;

    BigDecimal percentage;

    LocalDate date;
}
