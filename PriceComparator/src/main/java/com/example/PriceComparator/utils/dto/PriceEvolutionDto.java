package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PriceEvolutionDto(
        LocalDate date,
        BigDecimal price
) {
}
