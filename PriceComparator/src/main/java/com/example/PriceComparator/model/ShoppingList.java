package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "shopping_list")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    @ManyToOne
    private Store store;

    @OneToMany
    private List<ShoppingListItem> items;

    private BigDecimal totalPrice;
}
