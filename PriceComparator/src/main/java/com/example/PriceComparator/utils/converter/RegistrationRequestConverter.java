package com.example.PriceComparator.utils.converter;

import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.StoreRepository;
import com.example.PriceComparator.utils.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class RegistrationRequestConverter implements Converter<User, RegistrationRequest> {
    private final StoreRepository storeRepository;
    @Override
    public User createFromDto(RegistrationRequest dto) {
        return User.builder()
                .username(dto.username())
                .hashedPassword(dto.password())
                .email(dto.email())
                .preferredStores(new HashSet<>(storeRepository.findAll()))
                .build();
    }

    @Override
    public RegistrationRequest createFromEntity(User entity) {
        return null;
    }
}
