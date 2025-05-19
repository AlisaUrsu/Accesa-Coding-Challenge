package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;

public record ShoppingListItemDto (
        String productId,
        String productName,
        int quantity,
        BigDecimal finalPrice
) {
}
