package com.example.PriceComparator.dto;

import java.util.List;

public record StoreProductHistoryDto(
        String storeName,
        List<ProductHistoryDto> products
) {
}
