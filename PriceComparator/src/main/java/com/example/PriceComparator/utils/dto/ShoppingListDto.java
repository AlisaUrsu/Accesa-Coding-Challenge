package com.example.PriceComparator.utils.dto;

import java.util.List;

public record ShoppingListDto(
        String storeName,
        String listName,
        List<ShoppingListItemDto> items
) {
}
