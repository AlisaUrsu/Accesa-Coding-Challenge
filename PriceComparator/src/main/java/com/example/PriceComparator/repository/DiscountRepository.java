package com.example.PriceComparator.repository;

import com.example.PriceComparator.aop.FilterByStorePreferences;
import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.model.StoreProduct;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByStoreProduct(StoreProduct storeProduct);

    @FilterByStorePreferences
    @Query("SELECT d FROM Discount d ORDER BY d.percentage DESC")
    List<Discount> findTopByOrderByDiscountPercentageDesc();

    @FilterByStorePreferences
    List<Discount> findByFromDateAfter(LocalDate date);

    @Query("""
        SELECT d FROM Discount d
        WHERE d.storeProduct = :storeProduct
          AND d.fromDate <= :date
          AND d.toDate >= :date
        ORDER BY d.fromDate DESC
    """)
    Optional<Discount> findActiveDiscountForProduct(@Param("storeProduct") StoreProduct storeProduct,
                                                    @Param("date") LocalDate date);

}
