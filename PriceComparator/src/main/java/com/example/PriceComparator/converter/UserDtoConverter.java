package com.example.PriceComparator.converter;

import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class UserDtoConverter implements Converter<User, UserDto> {
    @Override
    public User createFromDto(UserDto dto) {
        return null;
    }

    @Override
    public UserDto createFromEntity(User entity) {
        Set<Store> stores = entity.getPreferredStores();
        List<String> preferredStoreNames = (stores == null) ? List.of() :
                stores.stream()
                        .map(Store::getName)
                        .toList();
        return new UserDto(
                entity.getUsername(),
                entity.getEmail(),
                preferredStoreNames
        );
    }
}
