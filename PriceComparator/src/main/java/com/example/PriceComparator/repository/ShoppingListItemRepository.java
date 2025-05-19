package com.example.PriceComparator.repository;

import com.example.PriceComparator.model.ShoppingListItem;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
}
