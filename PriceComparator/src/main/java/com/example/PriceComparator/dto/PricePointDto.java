package com.example.PriceComparator.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PricePointDto(
        LocalDate date,
        BigDecimal price,
        BigDecimal pricePerUnit
) {
}
