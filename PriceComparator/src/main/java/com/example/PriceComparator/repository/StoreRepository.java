package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.StoreProduct;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Hidden
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}