package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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
