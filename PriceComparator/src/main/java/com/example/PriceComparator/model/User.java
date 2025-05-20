package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

/**
 * A really basic class for User to "mock" their experience with the application. Users will see products and discounts
 * only for the selected/preferred stores
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_store_preferences",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id")
    )
    private Set<Store> preferredStores;
}
