package com.example.PriceComparator.converter;

import com.example.PriceComparator.model.PriceAlert;
import com.example.PriceComparator.service.ProductService;
import com.example.PriceComparator.dto.PriceAlertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceAlertConverter implements Converter<PriceAlert, PriceAlertRequest> {
    private final ProductService productService;
    @Override
    public PriceAlert createFromDto(PriceAlertRequest dto) {
        var product = productService.getProductById(dto.productId());
        return PriceAlert.builder()
                .product(product)
                .targetPrice(dto.targetPrice())
                .build();
    }

    @Override
    public PriceAlertRequest createFromEntity(PriceAlert entity) {
        return null;
    }
}
