package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.PriceAlert;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.PriceAlertService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.PriceAlertConverter;
import com.example.PriceComparator.utils.dto.PriceAlertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/alerts")
@Tag(name = "Price Alerts")
public class PriceAlertController {
    private final PriceAlertService priceAlertService;
    private final CurrentUserService userService;
    private final PriceAlertConverter priceAlertConverter;

    @Operation(
            description = "Create alert for product",
            summary = "Allows users to set a threshold for a product and the system will notify them when the price" +
                    "drops below it."
    )
    @PostMapping
    public Result<?> createAlert(@RequestBody PriceAlertRequest request) {
        User user = userService.getCurrentUser();
        PriceAlert alert = priceAlertConverter.createFromDto(request);
        alert.setUser(user);
        priceAlertService.addPriceAlert(alert);
        return new Result<>(true, HttpStatus.CREATED.value(), "Alert created.", null);
    }
}


