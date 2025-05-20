package com.example.PriceComparator.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
/**
 * Implementation for the composite key needed by PriceHistory class
 */
public class PriceHistoryKey implements Serializable {
    private Long store;
    private String product;
    private LocalDate date;

    public PriceHistoryKey() {}

    public PriceHistoryKey(Long store, String product, LocalDate date){
        this.store = store;
        this.product = product;
        this.date = date;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PriceHistoryKey that)) return false;
        return Objects.equals(store, that.store) &&
                Objects.equals(product, that.product) &&
                Objects.equals(date, that.date);
    }
}
