package com.example.PriceComparator.model;

import java.io.Serializable;
import java.util.Objects;

public class StoreProductKey implements Serializable {
    private Long store;
    private String product;
    public StoreProductKey() {}

    public StoreProductKey(Long store, String product){
        this.store = store;
        this.product = product;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StoreProductKey that)) return false;
        return Objects.equals(store, that.store) &&
                Objects.equals(product, that.product);
    }
}
