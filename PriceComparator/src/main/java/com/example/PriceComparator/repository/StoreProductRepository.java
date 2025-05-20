package com.example.PriceComparator.repository;

import com.example.PriceComparator.aop.FilterByStorePreferences;
import com.example.PriceComparator.model.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface StoreProductRepository extends JpaRepository<StoreProduct, StoreProductKey> {
    Optional<StoreProduct> findByStoreAndProduct(Store store, Product product);
    @FilterByStorePreferences
    List<StoreProduct> findByProduct(Product product);
    @FilterByStorePreferences
    List<StoreProduct> findByProductOrderByPricePerUnit(Product product);
    @FilterByStorePreferences
    @Query("SELECT s FROM StoreProduct s WHERE s.product.name = :productName ORDER BY s.pricePerUnit")
    List<StoreProduct> findTheBestByPricePerUnit(@Param("productName") String productName);
}
