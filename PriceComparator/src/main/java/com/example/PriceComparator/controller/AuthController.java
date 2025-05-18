package com.example.PriceComparator.controller;

import com.example.PriceComparator.config.SecurityUser;
import com.example.PriceComparator.model.Store;
import com.example.PriceComparator.model.User;
import com.example.PriceComparator.service.UserService;
import com.example.PriceComparator.utils.Result;
import com.example.PriceComparator.utils.converter.RegistrationRequestConverter;
import com.example.PriceComparator.utils.converter.UserDtoConverter;
import com.example.PriceComparator.utils.dto.LoginRequest;
import com.example.PriceComparator.utils.dto.RegistrationRequest;
import com.example.PriceComparator.utils.dto.UserDto;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final RegistrationRequestConverter registrationRequestConverter;
    private final AuthenticationManager authenticationManager;
    private final UserDtoConverter userDtoConverter;
    @PostMapping("/register")
    public Result<?> register(@RequestBody RegistrationRequest registrationRequest) {

        this.userService.addUser(registrationRequestConverter.createFromDto(registrationRequest));
        return new Result<>(true, HttpStatus.CREATED.value(), "User created successfully.", null);
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return new Result<>(true, HttpStatus.OK.value(), "Logged in.", null);
        } catch (BadCredentialsException ex) {
            return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "Bad credentials.", null);
        }
    }

    @GetMapping("/users")
    public Result<UserDto> currentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userDtoConverter.createFromEntity(userService.getUserByUsername(username));
        return new Result<>(true, HttpStatus.OK.value(), "Retrieved currently logged in user.", user);
    }

}
