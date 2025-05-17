package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.model.StoreProductKey;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Hidden
public interface StoreProductRepository extends JpaRepository<StoreProduct, StoreProductKey> {
}
