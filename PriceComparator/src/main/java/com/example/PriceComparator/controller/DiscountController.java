package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.service.DiscountService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.DiscountDtoConverter;
import com.example.PriceComparator.utils.dto.DiscountDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;
    private final DiscountDtoConverter discountDtoConverter;

    @GetMapping("/best")
    public Result<List<DiscountDto>> getBestDiscounts() {
        var discountsDto = discountService.getBestDiscounts().stream()
                .map(discountDtoConverter::createFromEntity)
                .toList();
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all discounted products based on the best " +
                "discounts.", discountsDto);
    }

    @GetMapping("/new")
    public Result<List<DiscountDto>> getNewDiscounts(@RequestParam(defaultValue = "1") int days) {
        LocalDate since = LocalDate.now().minusDays(days);
        var discountsDto = discountService.getNewDiscounts(since).stream()
                .map(discountDtoConverter::createFromEntity)
                .toList();
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved newly added discounts.", discountsDto);
    }
}
