package com.example.PriceComparator.service;

import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.StoreRepository;
import com.example.PriceComparator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final StoreRepository storeRepository;

    public User addUser(User user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .hashedPassword(passwordEncoder.encode(user.getHashedPassword()))
                .email(user.getEmail())
                .build();
        return userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with %d: not found: ", id)));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with %s: not found: ", username)));
    }

    public void updatePreferredStores(User user, Set<Long> storeIds) {
        Set<Store> selectedStores = new HashSet<>(storeRepository.findAllById(storeIds));
        user.setPreferredStores(selectedStores);
        userRepository.save(user);
    }

}
