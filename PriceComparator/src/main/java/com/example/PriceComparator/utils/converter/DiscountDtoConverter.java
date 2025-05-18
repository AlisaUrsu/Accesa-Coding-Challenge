package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.Discount;
import com.example.PriceComparator.utils.dto.DiscountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
public class DiscountDtoConverter implements Converter<Discount, DiscountDto> {
    @Override
    public DiscountDto createFromEntity(Discount entity) {
        var storeProduct = entity.getStoreProduct();
        var product = storeProduct.getProduct();
        var store = storeProduct.getStore();
        var unit = product.getPackageUnit();

        BigDecimal discountPercentage = entity.getPercentage();
        BigDecimal originalPrice = storeProduct.getPrice();
        BigDecimal discountedPrice = originalPrice
                .subtract(originalPrice.multiply(discountPercentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        BigDecimal originalPricePerUnit = storeProduct.getPricePerUnit();
        BigDecimal discountedPricePerUnit = originalPricePerUnit != null
                ? originalPricePerUnit
                .subtract(originalPricePerUnit.multiply(discountPercentage).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
                : null;

        return new DiscountDto(
                product.getId(),
                product.getName(),
                product.getBrand().getName(),
                store.getName(),
                originalPrice,
                product.getPackageQuantity(),
                unit.getName(),
                originalPricePerUnit,
                "RON/" + unit.getStandardUnit(),
                discountPercentage,
                discountedPrice,
                discountedPricePerUnit,
                entity.getFromDate(),
                entity.getToDate()
        );
    }

    @Override
    public Discount createFromDto(DiscountDto dto) {
        return null;
    }
}
