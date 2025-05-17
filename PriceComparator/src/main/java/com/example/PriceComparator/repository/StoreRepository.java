package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.Store;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Hidden
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
}