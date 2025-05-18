package com.example.PriceComparator.aop;

import com.example.PriceComparator.model.Store;

import java.util.Set;

public interface StoreFilterAware {
    void setAllowedStores(Set<Store> stores);
    Set<Store> getAllowedStores();
}