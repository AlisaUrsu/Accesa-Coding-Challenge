package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.User;
import com.example.PriceComparator.utils.dto.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestConverter implements Converter<User, RegistrationRequest> {
    @Override
    public User createFromDto(RegistrationRequest dto) {
        return User.builder()
                .username(dto.username())
                .hashedPassword(dto.password())
                .email(dto.email())
                .build();
    }

    @Override
    public RegistrationRequest createFromEntity(User entity) {
        return null;
    }
}
