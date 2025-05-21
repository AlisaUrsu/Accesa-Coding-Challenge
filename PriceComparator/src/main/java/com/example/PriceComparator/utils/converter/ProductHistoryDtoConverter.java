package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.model.Product;
import com.example.PriceComparator.utils.dto.PricePointDto;
import com.example.PriceComparator.utils.dto.ProductHistoryDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductHistoryDtoConverter implements Converter<List<PriceHistory>, ProductHistoryDto> {
    @Override
    public List<PriceHistory> createFromDto(ProductHistoryDto dto) {
        return null;
    }

    @Override
    public ProductHistoryDto createFromEntity(List<PriceHistory> entity) {
        Product product = entity.get(0).getProduct();
        String productId = product.getId();
        String productName = product.getName();

        List<PricePointDto> pricePoints = entity.stream()
                .map(new PricePointDtoConverter()::createFromEntity)
                .toList();

        return new ProductHistoryDto(
                productId,
                productName,
                product.getBrand().getName(),
                product.getCategory().getName(),
                pricePoints);
    }
}
