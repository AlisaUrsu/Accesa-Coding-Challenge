package com.example.PriceComparator.utils.dto;

import java.math.BigDecimal;

public record PriceAlertRequest(
        String productId,
        BigDecimal targetPrice
) {}