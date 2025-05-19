package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.ShoppingListItem;
import com.example.PriceComparator.utils.dto.ShoppingListItemDto;
import org.springframework.stereotype.Component;

@Component
public class ShoppingListItemDtoConverter implements Converter<ShoppingListItem, ShoppingListItemDto> {
    @Override
    public ShoppingListItemDto createFromEntity(ShoppingListItem entity) {
        return new ShoppingListItemDto(
                entity.getProduct().getId(),
                entity.getProduct().getName(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }

    @Override
    public ShoppingListItem createFromDto(ShoppingListItemDto dto) {
        return null;
    }
}
