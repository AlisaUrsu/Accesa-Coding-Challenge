package com.example.PriceComparator.aop;

import com.example.PriceComparator.model.Store;

import java.util.Set;

public class DiscountQueryParams implements StoreFilterAware {
    private Set<Store> allowedStores;

    @Override
    public void setAllowedStores(Set<Store> stores) {
        this.allowedStores = stores;
    }

    @Override
    public Set<Store> getAllowedStores() {
        return allowedStores;
    }
}
