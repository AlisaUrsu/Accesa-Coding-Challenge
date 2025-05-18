package com.example.PriceComparator.service;

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

    public List<StoreProduct> getStoreProductsByProduct(Product product){
        return  storeProductRepository.findByProduct(product);
    }
}
