package com.example.PriceComparator.utils.dto;

import java.util.List;

public record ProductTrendDto(
        String id,
        String productName,
        String brand,
        String category,
        List<PriceEvolutionDto> pricePoints
) {
}
