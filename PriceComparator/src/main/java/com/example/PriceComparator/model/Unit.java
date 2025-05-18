package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "units")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String standardUnit;

    @Column(precision = 10, scale = 3)
    private BigDecimal conversionFactor;
}
