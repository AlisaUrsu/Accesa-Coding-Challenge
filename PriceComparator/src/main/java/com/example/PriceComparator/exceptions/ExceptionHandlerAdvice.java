package com.example.PriceComparator.exceptions;

import com.example.PriceComparator.utils.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new Result<>(false, HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new Result<>(false, HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result<String> handleAuthenticationException(Exception ex) {
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result<String> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "Login credentials are missing.", ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result<String> handleAccountStatusException(AccountStatusException ex) {
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "User account is abnormal.", ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    Result<String> handleAccessDeniedException(AccessDeniedException ex) {
        return new Result<>(false, HttpStatus.FORBIDDEN.value(), "No permission.", ex.getMessage());
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result<String> handleAuthenticationCredentialsNotFoundException(org.springframework.security.authentication.AuthenticationCredentialsNotFoundException ex) {
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "Authentication is required.", ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new Result<>(false, HttpStatus.NOT_FOUND.value(), "This API endpoint is not found.", ex.getMessage());
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    Result<String> handleCredentialsExpiredException(CredentialsExpiredException ex) {
        return new Result<>(false, HttpStatus.UNAUTHORIZED.value(), "This API endpoint is not found.", ex.getMessage());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    Result<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        return new Result<>(false, HttpStatus.CONFLICT.value(), "Username is already taken", ex.getMessage());
    }

    /**
     * Fallback handles any unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Result handleOtherException(Exception ex) {
        return new Result(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), "");
    }
}
