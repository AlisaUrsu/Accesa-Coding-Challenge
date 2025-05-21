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
        BigDecimal basePrice = storeProduct.getPrice();

        // Logic: discountedPrice = basePrice * (1 - (percentage / 100))
        BigDecimal discountedPrice = basePrice.multiply(
                BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)));


        BigDecimal basePricePerUnit = storeProduct.getPricePerUnit();

        // Logic: discountedPricePerUnit = basePricePerUnit * (1 - (percentage / 100))
        BigDecimal discountedPricePerUnit = basePricePerUnit != null
                ? basePricePerUnit.multiply(
                        BigDecimal.ONE.subtract(discountPercentage.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)))
                : null;

        return new DiscountDto(
                product.getId(),
                product.getName(),
                product.getBrand().getName(),
                store.getName(),
                discountPercentage,
                entity.getFromDate(),
                entity.getToDate(),
                basePrice,
                product.getPackageQuantity(),
                unit.getName(),
                basePricePerUnit,
                "RON/" + unit.getStandardUnit(),
                discountedPrice,
                discountedPricePerUnit
        );
    }

    @Override
    public Discount createFromDto(DiscountDto dto) {
        return null;
    }
}
