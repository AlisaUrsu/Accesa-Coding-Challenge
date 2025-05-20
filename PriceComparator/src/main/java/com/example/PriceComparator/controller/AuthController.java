package com.example.PriceComparator.controller;

import com.example.PriceComparator.config.SecurityUser;
import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.UserService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.RegistrationRequestConverter;
import com.example.PriceComparator.utils.converter.UserDtoConverter;
import com.example.PriceComparator.utils.dto.LoginRequest;
import com.example.PriceComparator.utils.dto.RegistrationRequest;
import com.example.PriceComparator.utils.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.endpoint.base-url}/auth")
@Tag(name = "Authentication")
public class AuthController {
    private final UserService userService;
    private final RegistrationRequestConverter registrationRequestConverter;
    private final UserDtoConverter userDtoConverter;
    private final CurrentUserService currentUserService;

    @Operation(
            description = "Register endpoint",
            summary = "Allows users to create a new account"
    )
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegistrationRequest registrationRequest) {

        this.userService.addUser(registrationRequestConverter.createFromDto(registrationRequest));
        return new Result<>(true, HttpStatus.CREATED.value(), "User created successfully.", null);
    }


    @Operation(
            description = "Get current user endpoint",
            summary = "Retrieves data about the current user."
    )
    @GetMapping("/users")
    public Result<UserDto> currentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userDtoConverter.createFromEntity(userService.getUserByUsername(username));
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved currently logged in user.", user);
    }

    @Operation(
            description = "Select preferred stores",
            summary = "Lets users choose what stores they want to see."
    )
    @PostMapping("/preferred-stores")
    public Result<?> updatePreferredStores(@RequestBody Set<Long> storeIds) {
        User currentUser = currentUserService.getCurrentUser();
        userService.updatePreferredStores(currentUser, storeIds);
        return new Result<>(true, HttpStatus.OK.value(), "Preferred stores updated.", null);
    }

}
