package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;

public record ProductPriceComparisonDto(
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
