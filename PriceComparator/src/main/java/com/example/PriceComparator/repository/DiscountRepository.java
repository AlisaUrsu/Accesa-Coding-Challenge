package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.model.StoreProduct;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Transactional
    @Modifying
    @Query("""
    DELETE FROM Discount d
    WHERE d.storeProduct = :storeProduct
      AND d.toDate <= :fromDate
""")
    void deleteByStoreProductAndDateOverlap(
            @Param("storeProduct") StoreProduct storeProduct,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );

    Optional<Discount> findByStoreProduct(StoreProduct storeProduct);

    @Query("SELECT d FROM Discount d ORDER BY d.percentage DESC")
    List<Discount> findTopByOrderByDiscountPercentageDesc();

    List<Discount> findByFromDateAfter(LocalDate date);

    Optional<Discount> findFirstByStoreProductAndFromDateLessThanEqualAndToDateGreaterThanEqualOrderByFromDateDesc(
            StoreProduct storeProduct, LocalDate from, LocalDate to
    );

}
