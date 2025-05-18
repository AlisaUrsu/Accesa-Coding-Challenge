package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.DiscountHistory;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Hidden
public interface DiscountHistoryRepository extends JpaRepository<DiscountHistory, Long> {

}
