package com.example.PriceComparator.dto;

import java.math.BigDecimal;
import java.util.List;

public record ShoppingListDto(
        String storeName,
        String listName,
        List<ShoppingListItemDto> items,
        BigDecimal totalPrice
) {
}
