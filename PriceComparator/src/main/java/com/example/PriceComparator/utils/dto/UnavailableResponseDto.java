package com.example.PriceComparator.utils.dto;

import com.example.PriceComparator.model.ShoppingList;

import java.util.List;

public record UnavailableResponseDto(
        List<ShoppingListDto> shoppingLists,
        List<String> unavailable
) {
}
