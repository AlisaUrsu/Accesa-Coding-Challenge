package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.service.PriceEvolutionService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.PriceEvolutionDtoConverter;
import com.example.PriceComparator.utils.converter.StorePriceEvolutionDtoConverter;
import com.example.PriceComparator.utils.dto.PriceEvolutionDto;
import com.example.PriceComparator.utils.dto.StorePriceEvolutionDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price-trends")
@SecurityRequirement(name = "basicAuth")
public class PriceEvolutionController {
    private final PriceEvolutionService priceEvolutionService;
    private final StorePriceEvolutionDtoConverter storePriceEvolutionDtoConverter;

    @GetMapping("/{productId}")
    public Result<List<StorePriceEvolutionDto>> getPriceEvolutionForProduct(@PathVariable String productId) {
        List<PriceHistory> history = priceEvolutionService.getPriceTrendsById(productId);

        List<StorePriceEvolutionDto> priceEvolutionDtos = storePriceEvolutionDtoConverter.convertGroupedByStore(history);

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved price evolution for this product", priceEvolutionDtos);
    }
}
