package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.model.PriceHistoryKey;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, PriceHistoryKey> {
    List<PriceHistory> findByProductIdOrderByDateAsc(String id);

    @Query("""
    SELECT ph FROM PriceHistory ph
    WHERE (:storeName IS NULL OR ph.store.name = :storeName)
      AND (:brandName IS NULL OR ph.product.brand.name = :brandName)
      AND (:categoryName IS NULL OR ph.product.category.name = :categoryName)
    ORDER BY ph.product.id, ph.date ASC
    """)
    List<PriceHistory> findByFilters(
            @Param("storeName") String storeName,
            @Param("brandName") String brandName,
            @Param("categoryName") String categoryName);

}
