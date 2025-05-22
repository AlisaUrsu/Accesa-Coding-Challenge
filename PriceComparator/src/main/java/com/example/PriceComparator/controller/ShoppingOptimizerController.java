package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.ShoppingList;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.ShoppingOptimizerService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.converter.ShoppingListDtoConverter;
import com.example.PriceComparator.dto.BasketItemDto;
import com.example.PriceComparator.dto.ShoppingListDto;
import com.example.PriceComparator.dto.ShoppingListInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("${api.endpoint.base-url}/shopping")
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Shopping Optimizer")
public class ShoppingOptimizerController {
    private final ShoppingOptimizerService shoppingOptimizerService;
    private final CurrentUserService currentUserService;
    private final ShoppingListDtoConverter shoppingListDtoConverter;

    @Operation(
            description = "Generate optimized lists",
            summary = "Allows users to select some products and the system returns an optimized shopping list " +
                    "for every store."
    )
    @PostMapping("/optimize")
    public Result<ShoppingListInfoDto> optimizeShopping(@RequestBody List<BasketItemDto> basket) {
        User user = currentUserService.getCurrentUser();
        List<String> unavailable = new ArrayList<>();
        List<ShoppingList> lists = shoppingOptimizerService.generateOptimizedLists(user, basket, unavailable);
        List<ShoppingListDto> listDtos = lists.stream()
                .map(shoppingListDtoConverter::createFromEntity)
                .toList();

        String message = unavailable.isEmpty()
                ? "Optimized basket."
                : "Optimized basket. Some products were not available in your preferred stores.";

        ShoppingListInfoDto responseDto = new ShoppingListInfoDto(listDtos, unavailable);

        return new Result<>(true, HttpStatus.CREATED.value(), message, responseDto);
    }
}
