package com.example.PriceComparator.converter;

import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.StoreRepository;
import com.example.PriceComparator.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
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
