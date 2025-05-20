package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.utils.dto.PriceEvolutionDto;
import com.example.PriceComparator.utils.dto.ProductTrendDto;
import com.example.PriceComparator.utils.dto.StorePriceEvolutionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StorePriceEvolutionDtoConverter implements Converter<List<PriceHistory>, StorePriceEvolutionDto> {
    @Override
    public StorePriceEvolutionDto createFromEntity(List<PriceHistory> entity) {

        return null;
    }

    @Override
    public List<PriceHistory> createFromDto(StorePriceEvolutionDto dto) {
        return null;
    }

    public List<StorePriceEvolutionDto> convertGroupedByStore(List<PriceHistory> history) {
        if (history.isEmpty()) {
            return List.of();
        }

        Map<String, List<PriceHistory>> byStore = history.stream()
                .collect(Collectors.groupingBy(ph -> ph.getStore().getName()));

        return byStore.entrySet().stream()
                .map(storeEntry -> {
                    String storeName = storeEntry.getKey();
                    List<PriceHistory> storeHistories = storeEntry.getValue();

                    Map<String, List<PriceHistory>> byProduct = storeHistories.stream()
                            .collect(Collectors.groupingBy(ph -> ph.getProduct().getId()));

                    List<ProductTrendDto> productTrendDtos = byProduct.values().stream()
                            .map(new ProductTrendDtoConverter()::createFromEntity)
                            .toList();

                    return new StorePriceEvolutionDto(storeName, productTrendDtos);
                })
                .toList();
    }
}
