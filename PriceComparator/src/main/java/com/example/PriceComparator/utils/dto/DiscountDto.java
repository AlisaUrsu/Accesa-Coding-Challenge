package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiscountDto(
        String productId,
        String productName,
        String brandName,
        String storeName,
        BigDecimal discountPercentage,
        LocalDate fromDate,
        LocalDate toDate,
        BigDecimal originalPrice,
        BigDecimal packageQuantity,
        String unit,
        BigDecimal originalPricePerUnit,
        String standardUnit,
        BigDecimal discountedPrice,
        BigDecimal discountedPricePerUnit
) {
}
