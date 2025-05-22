package com.example.PriceComparator.dto;

import java.util.List;

public record ProductHistoryDto(
        String id,
        String productName,
        String brand,
        String category,
        List<PricePointDto> pricePoints
) {
}
