package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.service.ProductService;
import com.example.PriceComparator.service.StoreProductsService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.ProductPriceComparisonDtoConverter;
import com.example.PriceComparator.utils.dto.ProductPriceComparisonDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
public class ProductController {
    private final StoreProductsService storeProductsService;
    private final ProductPriceComparisonDtoConverter priceComparisonDtoConverter;
    private final ProductService productService;

    @GetMapping("/compare/{productId}")
    public Result<List<ProductPriceComparisonDto>> compareProductPrice(@PathVariable String productId) {
        var product = productService.getProductById(productId);

        List<StoreProduct> storeProducts = storeProductsService.getStoreProductsByProduct(product);

        if (storeProducts.isEmpty()) {
            return new Result<>(false, HttpStatus.NOT_FOUND.value(), "No stores found for this product.", List.of());
        }


        List<ProductPriceComparisonDto> comparisonDtos = storeProducts.stream()
                .map(priceComparisonDtoConverter::createFromEntity)
                .toList();

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all prices for this product.", comparisonDtos);
    }

    @GetMapping("/{productName}")
    public Result<List<ProductPriceComparisonDto>> compareProductPricePerUnit(@PathVariable String productName) {
        List<StoreProduct> storeProducts = storeProductsService.getBestByPricePerUnit(productName);

        if (storeProducts.isEmpty()) {
            return new Result<>(false, HttpStatus.NOT_FOUND.value(), "No stores found for this product.", List.of());
        }


        List<ProductPriceComparisonDto> comparisonDtos = storeProducts.stream()
                .map(priceComparisonDtoConverter::createFromEntity)
                .toList();

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all prices for this product.", comparisonDtos);
    }
}
