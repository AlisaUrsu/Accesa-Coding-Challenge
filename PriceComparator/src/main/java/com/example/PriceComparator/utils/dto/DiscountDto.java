package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiscountDto(
        String productId,
        String productName,
        String brandName,
        String storeName,
        BigDecimal originalPrice,
        BigDecimal packageQuantity,
        String unit,
        BigDecimal originalPricePerUnit,
        String standardUnit,
        BigDecimal discountPercentage,
        BigDecimal discountedPrice,
        BigDecimal discountedPricePerUnit,
        LocalDate fromDate,
        LocalDate toDate
) {
}
