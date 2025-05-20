package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * This class is for stores. New Stores are added by .csv filename
 */
@Entity
@Table(name = "stores")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store store)) return false;
        return id != null && id.equals(store.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
