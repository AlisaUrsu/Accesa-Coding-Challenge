package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.ShoppingList;
import com.example.PriceComparator.utils.dto.ShoppingListDto;
import com.example.PriceComparator.utils.dto.ShoppingListItemDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingListDtoConverter implements Converter<ShoppingList, ShoppingListDto> {
    @Override
    public ShoppingListDto createFromEntity(ShoppingList entity) {
        List<ShoppingListItemDto> items = entity.getItems().stream()
                .map(new ShoppingListItemDtoConverter()::createFromEntity)
                .toList();
        return new ShoppingListDto(
                entity.getStore().getName(),
                entity.getName(),
                items,
                entity.getTotalPrice()
        );
    }

    @Override
    public ShoppingList createFromDto(ShoppingListDto dto) {
        return null;
    }
}
