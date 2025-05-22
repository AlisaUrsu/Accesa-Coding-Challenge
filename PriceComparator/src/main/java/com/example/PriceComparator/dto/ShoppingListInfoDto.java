package com.example.PriceComparator.dto;

import java.util.List;

public record ShoppingListInfoDto(
        List<ShoppingListDto> shoppingLists,
        List<String> unavailable
) {
}
