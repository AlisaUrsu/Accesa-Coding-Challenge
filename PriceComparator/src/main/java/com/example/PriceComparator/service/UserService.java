package com.example.PriceComparator.service;

import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .hashedPassword(passwordEncoder.encode(user.getHashedPassword()))
                .email(user.getEmail())
                .build();
        userRepository.save(newUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with %d: not found: ", id)));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with %s: not found: ", username)));
    }

}
