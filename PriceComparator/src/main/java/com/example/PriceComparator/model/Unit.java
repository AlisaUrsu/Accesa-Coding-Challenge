package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * This class was created with the intention to simplify the process of computing the price per units by knowing
 * the conversion factor for different types of units
 */
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

    // g -> kg; kg -> kg; ml -> l; l -> l; role/buc -> rola/buc
    private String standardUnit;

    @Column(precision = 10, scale = 3)
    private BigDecimal conversionFactor;
}
