package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.service.StoreProductsService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.converter.ProductDtoConverter;
import com.example.PriceComparator.dto.ProductDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/products")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Products")
public class ProductController {
    private final StoreProductsService storeProductsService;
    private final ProductDtoConverter productDtoConverter;

    @Operation(
            description = "Compare prices by product id",
            summary = "Displays prices for a product across all stores. Sorted by best price per unit."
    )
    @GetMapping("/compare/{productId}")
    public Result<List<ProductDto>> compareProductPriceById(@PathVariable String productId) {

        List<StoreProduct> storeProducts = storeProductsService.getStoreProductsByProductId(productId);

        if (storeProducts.isEmpty()) {
            return new Result<>(false, HttpStatus.NOT_FOUND.value(), "No stores found for this product.", List.of());
        }

        List<ProductDto> comparisonDtos = storeProducts.stream()
                .map(productDtoConverter::createFromEntity)
                .sorted(Comparator.comparing(ProductDto::discountedPricePerUnit))
                .toList();

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all prices for this product.", comparisonDtos);
    }
    @Operation(
            description = "Compare prices by product name",
            summary = "Displays prices for products with the same name even if the quantity differs. " +
                    "Sorted by best price per unit."
    )
    @GetMapping("/{productName}")
    public Result<List<ProductDto>> compareProductPriceByName(@PathVariable String productName) {
        List<StoreProduct> storeProducts = storeProductsService.getStoreProductsByProductName(productName);

        if (storeProducts.isEmpty()) {
            return new Result<>(false, HttpStatus.NOT_FOUND.value(), "No stores found for this product.", List.of());
        }

        List<ProductDto> comparisonDtos = storeProducts.stream()
                .map(productDtoConverter::createFromEntity)
                .sorted(Comparator.comparing(ProductDto::discountedPricePerUnit))
                .toList();

        return new Result<>(true, HttpStatus.OK.value(), "Retrieved all prices for this product.", comparisonDtos);
    }
}
