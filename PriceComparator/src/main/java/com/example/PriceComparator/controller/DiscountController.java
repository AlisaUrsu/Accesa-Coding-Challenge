package com.example.PriceComparator.controller;

import com.example.PriceComparator.service.DiscountService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.converter.DiscountDtoConverter;
import com.example.PriceComparator.dto.DiscountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/discounts")
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Discounts")
public class DiscountController {
    private final DiscountService discountService;
    private final DiscountDtoConverter discountDtoConverter;

    @Operation(
            description = "Best discounts",
            summary = "Retrieves every discount sorted in descending order. Discounted price and price per unit are also" +
                    "displayed."
    )
    @GetMapping("/best")
    public Result<List<DiscountDto>> getBestDiscounts() {
        List<DiscountDto> discountsDto = discountService.getBestDiscounts(LocalDate.now()).stream()
                .map(discountDtoConverter::createFromEntity)
                .toList();
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all discounted products based on the best " +
                "discounts.", discountsDto);
    }

    @Operation(
            description = "New discounts",
            summary = "Retrieves only the discounts that appeared a day ago, or more days ago. Discounted price and " +
                    "price per unit are also displayed."
    )
    @GetMapping("/new")
    public Result<List<DiscountDto>> getNewDiscounts(@RequestParam(defaultValue = "1") int days) {
        LocalDate since = LocalDate.now().minusDays(days);

        List<DiscountDto> discountsDto = discountService.getNewDiscounts(since).stream()
                .map(discountDtoConverter::createFromEntity)
                .toList();
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved newly added discounts.", discountsDto);
    }
}
