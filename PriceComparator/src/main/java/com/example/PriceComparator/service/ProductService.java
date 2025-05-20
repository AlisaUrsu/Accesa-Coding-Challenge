package com.example.PriceComparator.service;

import com.example.PriceComparator.model.Product;
import com.example.PriceComparator.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with %s: not found: ", id)));
    }


}
