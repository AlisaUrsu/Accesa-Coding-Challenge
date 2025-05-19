package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.model.PriceHistoryKey;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, PriceHistoryKey> {
    List<PriceHistory> findByProductIdOrderByDateAsc(String id);
}
