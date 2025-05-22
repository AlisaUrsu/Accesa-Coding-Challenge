package com.example.PriceComparator.controller;

import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.CurrentUserService;
import com.example.PriceComparator.service.UserService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.converter.RegistrationRequestConverter;
import com.example.PriceComparator.converter.UserDtoConverter;
import com.example.PriceComparator.dto.RegistrationRequest;
import com.example.PriceComparator.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Result<UserDto> register(@RequestBody RegistrationRequest registrationRequest) {
        User user = userService.addUser(registrationRequestConverter.createFromDto(registrationRequest));
        UserDto userDto = userDtoConverter.createFromEntity(user);
        return new Result<>(true, HttpStatus.CREATED.value(), "User created successfully.", userDto);
    }


    @Operation(
            description = "Get current user endpoint",
            summary = "Retrieves data about the current user."
    )
    @GetMapping("/users")
    public Result<UserDto> currentUser() {
        User user = currentUserService.getCurrentUser();
        UserDto userDto = userDtoConverter.createFromEntity(user);
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved currently logged in user.", userDto);
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
