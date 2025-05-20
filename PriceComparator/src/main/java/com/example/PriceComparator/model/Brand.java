package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class representing the brand of a product. Stored separately to provide extensibility for future changes
 */
@Entity
@Table(name = "brands")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}