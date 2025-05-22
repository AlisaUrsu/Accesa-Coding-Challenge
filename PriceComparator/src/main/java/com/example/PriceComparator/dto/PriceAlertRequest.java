package com.example.PriceComparator.dto;

import java.math.BigDecimal;

public record PriceAlertRequest(
        String productId,
        BigDecimal targetPrice
) {}