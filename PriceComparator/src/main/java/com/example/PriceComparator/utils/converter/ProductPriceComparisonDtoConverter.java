package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.model.StoreProduct;
import com.example.PriceComparator.repository.DiscountRepository;
import com.example.PriceComparator.utils.dto.ProductPriceComparisonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductPriceComparisonDtoConverter implements Converter<StoreProduct, ProductPriceComparisonDto> {
    private final DiscountRepository discountRepository;
    @Override
    public ProductPriceComparisonDto createFromEntity(StoreProduct entity) {
        var product = entity.getProduct();
        var store = entity.getStore();
        BigDecimal originalPrice = entity.getPrice();
        BigDecimal quantity = product.getPackageQuantity();
        var unit = product.getPackageUnit();

        LocalDate today = LocalDate.now();

        // Find current discount if any
        Optional<Discount> optionalDiscount = discountRepository
                .findActiveDiscountForProduct(entity, LocalDate.now());

        BigDecimal discountPercentage = optionalDiscount
                .map(Discount::getPercentage)
                .orElse(BigDecimal.ZERO);

        BigDecimal discountedPrice = originalPrice;
        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discountMultiplier = BigDecimal.ONE.subtract(
                    discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
            );
            discountedPrice = originalPrice.multiply(discountMultiplier)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        // Price per unit
        BigDecimal originalPricePerUnit = entity.getPricePerUnit();
        BigDecimal discountedPricePerUnit;
        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            // handle gracefully, maybe log or return null / BigDecimal.ZERO
            discountedPricePerUnit = discountedPrice.divide(quantity, 4, RoundingMode.HALF_UP);
        }
        discountedPricePerUnit = originalPricePerUnit;

        return new ProductPriceComparisonDto(
                store.getName(),
                originalPrice,
                quantity,
                unit.getName(),
                originalPricePerUnit,
                "RON/" + unit.getStandardUnit(),
                discountPercentage,
                discountedPrice,
                discountedPricePerUnit
        );
    }

    @Override
    public StoreProduct createFromDto(ProductPriceComparisonDto dto) {
        return null;
    }
}
