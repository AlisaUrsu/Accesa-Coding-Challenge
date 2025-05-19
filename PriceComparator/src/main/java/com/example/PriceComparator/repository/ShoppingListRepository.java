package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.ShoppingList;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
}
