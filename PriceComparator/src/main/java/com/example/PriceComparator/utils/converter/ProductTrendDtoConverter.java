package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.model.Product;
import com.example.PriceComparator.utils.dto.PriceEvolutionDto;
import com.example.PriceComparator.utils.dto.ProductTrendDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductTrendDtoConverter implements Converter<List<PriceHistory>, ProductTrendDto> {
    @Override
    public List<PriceHistory> createFromDto(ProductTrendDto dto) {
        return null;
    }

    @Override
    public ProductTrendDto createFromEntity(List<PriceHistory> entity) {
        Product product = entity.get(0).getProduct();
        String productId = product.getId();
        String productName = product.getName();
        List<PriceEvolutionDto> pricePoints = entity.stream()
                .map(new PriceEvolutionDtoConverter()::createFromEntity)
                .toList();

        return new ProductTrendDto(productId, productName, product.getBrand().getName(), product.getCategory().getName(), pricePoints);
    }
}
