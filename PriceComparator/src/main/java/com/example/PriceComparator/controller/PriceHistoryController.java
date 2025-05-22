package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.service.PriceHistoryService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.converter.StoreProductHistoryDtoConverter;
import com.example.PriceComparator.dto.StoreProductHistoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/price-trends")
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Price History")
public class PriceHistoryController {
    private final PriceHistoryService priceHistoryService;
    private final StoreProductHistoryDtoConverter storeProductHistoryDtoConverter;

    @Operation(
            description = "Price evolution for product",
            summary = "Lets users check how the price evolved for a product across all stores."
    )
    @GetMapping("/{productId}")
    public Result<List<StoreProductHistoryDto>> getPriceEvolutionForProduct(@PathVariable String productId) {
        List<PriceHistory> history = priceHistoryService.getPriceTrendsById(productId);

        List<StoreProductHistoryDto> productHistoryDtos = storeProductHistoryDtoConverter.convertGroupedByStore(history);

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved price evolution for this product.", productHistoryDtos);
    }

    @Operation(
            description = "Price evolution for products",
            summary = "Displays price trends for every product across every store. Data can be filtered by store, brand " +
                    "and category."
    )
    @GetMapping
    public Result<List<StoreProductHistoryDto>> getPriceTrends(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String categoryName) {

        var priceHistories = priceHistoryService.getPriceTrendsFiltered(storeName, brandName, categoryName);
        List<StoreProductHistoryDto> productHistoryDtos = storeProductHistoryDtoConverter.convertGroupedByStore(priceHistories);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved every history of prices for every product.",
                productHistoryDtos);
    }
}
