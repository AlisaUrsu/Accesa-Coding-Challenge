package com.example.PriceComparator.utils.dto;

import java.util.List;

public record StoreProductHistoryDto(
        String storeName,
        List<ProductHistoryDto> products
) {
}
