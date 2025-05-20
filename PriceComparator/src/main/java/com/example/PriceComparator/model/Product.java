package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * This class stores products read from .csv files. This class contains only general information about the product,
 * like brand, category (both separate entities), package quantity and unit (separate entity)
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String id;

    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

    private BigDecimal packageQuantity;

    @ManyToOne
    private Unit packageUnit;
}
