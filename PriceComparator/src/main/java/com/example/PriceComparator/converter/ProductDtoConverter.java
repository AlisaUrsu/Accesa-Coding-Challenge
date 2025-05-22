package com.example.PriceComparator.converter;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.repository.DiscountRepository;
import com.example.PriceComparator.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductDtoConverter implements Converter<StoreProduct, ProductDto> {
    private final DiscountRepository discountRepository;
    @Override
    public ProductDto createFromEntity(StoreProduct entity) {
        var product = entity.getProduct();
        var store = entity.getStore();
        BigDecimal basePrice = entity.getPrice();
        BigDecimal quantity = product.getPackageQuantity();
        var unit = product.getPackageUnit();


        // Find current discount if any
        Optional<Discount> optionalDiscount = discountRepository
                .findActiveDiscountForProduct(entity, LocalDate.now());

        BigDecimal discountPercentage = optionalDiscount
                .map(Discount::getPercentage)
                .orElse(BigDecimal.ZERO);

        BigDecimal discountedPrice = basePrice;

        // Apply discount if greater than 0
        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {

            // Logic: discountedPrice = basePrice * (1 - (percentage / 100))
            discountedPrice = basePrice.multiply(
                    BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            );
        }

        // Compute price per unit
        BigDecimal basePricePerUnit = entity.getPricePerUnit();

        // Logic: discountedPricePerUnit = basePricePerUnit * (1 - (percentage / 100))
        BigDecimal discountedPricePerUnit = basePricePerUnit != null
                ? basePricePerUnit.multiply(
                BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                : null;

        return new ProductDto(
                store.getName(),
                basePrice,
                quantity,
                unit.getName(),
                basePricePerUnit,
                "RON/" + unit.getStandardUnit(),
                discountPercentage,
                discountedPrice,
                discountedPricePerUnit
        );
    }

    @Override
    public StoreProduct createFromDto(ProductDto dto) {
        return null;
    }
}
