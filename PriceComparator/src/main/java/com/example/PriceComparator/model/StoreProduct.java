package com.example.PriceComparator.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Class representing a m:n relationship between Store and Product. Because products can be found at multiple stores,
 * their price also varies, so I think it's better to keep specific information like this in a different table.
 * Only the most recent data (based on the date of the .csv file) is stored in this table.
 * Price per unit is computed when data is read from a file.
 * This table has a composite primary key based on store_id and product_id
 */
@Entity
@Table(name = "storeProducts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(StoreProductKey.class)
public class StoreProduct {
    @Id
    @ManyToOne
    private Store store;

    @Id
    @ManyToOne
    private Product product;

    private LocalDate date;

    private BigDecimal price;

    private String currency;

    private BigDecimal pricePerUnit;
}
