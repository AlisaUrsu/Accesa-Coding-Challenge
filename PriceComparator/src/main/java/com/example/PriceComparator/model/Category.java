package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Class representing the category of a product. Stored separately to provide extensibility for future changes
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
