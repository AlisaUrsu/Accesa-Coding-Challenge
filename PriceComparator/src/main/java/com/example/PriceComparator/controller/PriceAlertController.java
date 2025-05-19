package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.PriceAlert;
import com.example.PriceComparator.model.Product;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.PriceAlertService;
import com.example.PriceComparator.service.ProductService;
import com.example.PriceComparator.service.UserService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.PriceAlertConverter;
import com.example.PriceComparator.utils.dto.PriceAlertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alerts")
public class PriceAlertController {

    private final PriceAlertService priceAlertService;
    private final ProductService productService;
    private final CurrentUserService userService;
    private final PriceAlertConverter priceAlertConverter;

    @PostMapping
    public Result<?> createAlert(@RequestBody PriceAlertRequest request) {
        User user = userService.getCurrentUser();
        var alert = priceAlertConverter.createFromDto(request);
        alert.setUser(user);
        priceAlertService.addPriceAlert(alert);
        return new Result<>(true, HttpStatus.CREATED.value(), "Alert created.", null);
    }
}


