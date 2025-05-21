package com.example.PriceComparator.service;

import com.example.PriceComparator.config.SecurityUser;
import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CurrentUserService {

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof SecurityUser)) {
            throw new IllegalStateException("No authenticated user");
        }
        return ((SecurityUser) auth.getPrincipal()).getUser();
    }

    public Set<Store> getPreferredStores() {
        User user = getCurrentUser();
        return user.getPreferredStores();
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
    }

}
