package com.example.PriceComparator.service;

import com.example.PriceComparator.aop.FilterByStorePreferences;
import com.example.PriceComparator.model.Product;
import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.repository.StoreProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreProductsService {
    private final StoreProductRepository storeProductRepository;

    @FilterByStorePreferences
    public List<StoreProduct> getStoreProductsByProductId(String productId){
        return storeProductRepository.findByProductId(productId);
    }

    @FilterByStorePreferences
    public List<StoreProduct> getStoreProductsByProductName(String productName) {
        return storeProductRepository.findByProductName(productName);
    }
}
