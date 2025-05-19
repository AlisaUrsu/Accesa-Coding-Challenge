package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.utils.dto.PriceEvolutionDto;
import com.example.PriceComparator.utils.dto.StorePriceEvolutionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, List<PriceEvolutionDto>> grouped = new HashMap<>();

        for (PriceHistory h : history) {
            grouped
                    .computeIfAbsent(h.getStore().getName(), k -> new ArrayList<>())
                    .add(new PriceEvolutionDto(h.getDate(), h.getPrice()));
        }

        return grouped.entrySet().stream()
                .map(e -> new StorePriceEvolutionDto(e.getKey(), e.getValue()))
                .toList();
    }
}
