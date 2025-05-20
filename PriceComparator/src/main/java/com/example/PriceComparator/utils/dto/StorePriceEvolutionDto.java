package com.example.PriceComparator.utils.dto;

import java.util.List;

public record StorePriceEvolutionDto(
        String storeName,
        List<ProductTrendDto> products
) {
}
