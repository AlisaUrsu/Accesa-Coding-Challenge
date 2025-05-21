package com.example.PriceComparator.utils.dto;

import java.util.List;

public record ShoppingListInfoDto(
        List<ShoppingListDto> shoppingLists,
        List<String> unavailable
) {
}
