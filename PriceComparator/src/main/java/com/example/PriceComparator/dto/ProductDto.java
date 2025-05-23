package com.example.PriceComparator.dto;

import java.math.BigDecimal;

public record ProductDto(
        String storeName,
        BigDecimal originalPrice,
        BigDecimal packageQuantity,
        String unit,
        BigDecimal originalPricePerUnit,
        String standardUnit,
        BigDecimal discountPercentage,
        BigDecimal discountedPrice,
        BigDecimal discountedPricePerUnit
) {
}
