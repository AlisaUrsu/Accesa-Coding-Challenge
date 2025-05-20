package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.utils.dto.PriceEvolutionDto;
import org.springframework.stereotype.Component;

@Component
public class PriceEvolutionDtoConverter implements Converter<PriceHistory, PriceEvolutionDto> {
    @Override
    public PriceHistory createFromDto(PriceEvolutionDto dto) {
        return null;
    }

    @Override
    public PriceEvolutionDto createFromEntity(PriceHistory entity) {
        return new PriceEvolutionDto(
                entity.getDate(),
                entity.getPrice(),
                entity.getPricePerUnit()
        );
    }
}
