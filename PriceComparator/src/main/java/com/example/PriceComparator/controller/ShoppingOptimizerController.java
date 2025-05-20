package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.ShoppingList;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.ShoppingOptimizerService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.ShoppingListDtoConverter;
import com.example.PriceComparator.utils.dto.BasketItemDto;
import com.example.PriceComparator.utils.dto.ShoppingListDto;
import com.example.PriceComparator.utils.dto.UnavailableResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shopping")
@SecurityRequirement(name = "basicAuth")
public class ShoppingOptimizerController {
    private final ShoppingOptimizerService shoppingOptimizerService;
    private final CurrentUserService currentUserService;
    private final ShoppingListDtoConverter shoppingListDtoConverter;

    @PostMapping("/optimize")
    public Result<UnavailableResponseDto> optimizeShopping(@RequestBody List<BasketItemDto> basket) {
        User user = currentUserService.getCurrentUser();
        List<String> unavailable = new ArrayList<>();
        List<ShoppingList> lists = shoppingOptimizerService.generateOptimizedLists(user, basket, unavailable);
        List<ShoppingListDto> listDtos = lists.stream()
                .map(shoppingListDtoConverter::createFromEntity)
                .toList();

        String message = unavailable.isEmpty()
                ? "Optimized basket."
                : "Optimized basket. Some products were not available in your preferred stores.";

        UnavailableResponseDto responseDto = new UnavailableResponseDto(listDtos, unavailable);

        return new Result<>(true, HttpStatus.CREATED.value(), message, responseDto);
    }
}
