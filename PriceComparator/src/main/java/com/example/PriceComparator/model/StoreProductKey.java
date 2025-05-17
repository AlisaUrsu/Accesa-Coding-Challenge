package com.example.PriceComparator.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class StoreProductKey implements Serializable {
    private Long store;
    private String product;
    private LocalDate date;

    public StoreProductKey() {}

    public StoreProductKey(Long store, String product, LocalDate date){
        this.store = store;
        this.product = product;
        this.date = date;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StoreProductKey that)) return false;
        return Objects.equals(store, that.store) &&
                Objects.equals(product, that.product) &&
                Objects.equals(date, that.date);
    }
}
