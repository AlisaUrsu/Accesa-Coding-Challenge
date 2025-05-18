package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.User;
import com.example.PriceComparator.utils.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter implements Converter<User, UserDto> {
    @Override
    public User createFromDto(UserDto dto) {
        return null;
    }

    @Override
    public UserDto createFromEntity(User entity) {
        return new UserDto(
                entity.getUsername(),
                entity.getEmail()
        );
    }
}
