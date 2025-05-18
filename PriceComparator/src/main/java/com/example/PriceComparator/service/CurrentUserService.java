package com.example.PriceComparator.service;

import com.example.PriceComparator.config.SecurityUser;
import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof SecurityUser)) {
            throw new IllegalStateException("No authenticated user");
        }
        return ((SecurityUser) auth.getPrincipal()).getUser();
    }

    public Set<Store> getPreferredStores() {
        User user = getCurrentUser();
        Set<Store> stores = user.getPreferredStores();  // Check if this is populated
        System.out.println("User's preferred stores: " + stores);
        return stores;

    }
}
