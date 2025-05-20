package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.service.PriceHistoryService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.StorePriceEvolutionDtoConverter;
import com.example.PriceComparator.utils.dto.StorePriceEvolutionDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price-trends")
@SecurityRequirement(name = "basicAuth")
public class PriceEvolutionController {
    private final PriceHistoryService priceEvolutionService;
    private final StorePriceEvolutionDtoConverter storePriceEvolutionDtoConverter;

    @GetMapping("/{productId}")
    public Result<List<StorePriceEvolutionDto>> getPriceEvolutionForProduct(@PathVariable String productId) {
        List<PriceHistory> history = priceEvolutionService.getPriceTrendsById(productId);

        List<StorePriceEvolutionDto> priceEvolutionDtos = storePriceEvolutionDtoConverter.convertGroupedByStore(history);

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved price evolution for this product", priceEvolutionDtos);
    }

    @GetMapping
    public Result<List<StorePriceEvolutionDto>> getPriceTrends(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String categoryName) {

        var priceHistories = priceEvolutionService.getPriceTrendsFiltered(storeName, brandName, categoryName);

        List<StorePriceEvolutionDto> priceEvolutionDtos = storePriceEvolutionDtoConverter.convertGroupedByStore(priceHistories);

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved price evolution for this product", priceEvolutionDtos);
    }
}
