package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.PriceHistory;
import com.example.PriceComparator.utils.dto.PricePointDto;
import org.springframework.stereotype.Component;

@Component
public class PricePointDtoConverter implements Converter<PriceHistory, PricePointDto> {
    @Override
    public PriceHistory createFromDto(PricePointDto dto) {
        return null;
    }

    @Override
    public PricePointDto createFromEntity(PriceHistory entity) {
        return new PricePointDto(
                entity.getDate(),
                entity.getPrice(),
                entity.getPricePerUnit()
        );
    }
}
