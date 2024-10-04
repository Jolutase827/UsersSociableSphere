package com.sociablesphere.usersociablesphere.exceptions;

import jdk.jshell.spi.ExecutionControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;
    private WebRequest webRequest;

    @BeforeEach
    public void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
        webRequest = mock(WebRequest.class);
    }

    @Test
    @DisplayName("Handle HttpMessageConversionException")
    public void testHandleHttpMessageConversionException() {
        // Arrange
        HttpMessageConversionException exception = new HttpMessageConversionException("Invalid JSON");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleHttpMessageConversionException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getMessage()).contains("Invalid JSON format");
    }

    @Test
    @DisplayName("Handle NumberFormatException")
    public void testHandleNumberFormatException() {
        // Arrange
        NumberFormatException exception = new NumberFormatException("For input string: \"abc\"");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleNumberFormatException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid input");
    }

    @Test
    @DisplayName("Handle InvalidCredentialsException")
    public void testHandleInvalidCredentialsException() {
        // Arrange
        InvalidCredentialsException exception = new InvalidCredentialsException("Invalid username or password");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleInvalidCredentialsException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getBody().getMessage()).isEqualTo("Invalid credentials");
    }

    @Test
    @DisplayName("Handle UserAlreadyExistsException")
    public void testHandleUserAlreadyExistsException() {
        // Arrange
        UserAlreadyExistsException exception = new UserAlreadyExistsException("User already exists");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleUserAlreadyExistsException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(response.getBody().getMessage()).isEqualTo("User already exists");
    }

    @Test
    @DisplayName("Handle MethodArgumentNotValidException")
    public void testHandleMethodArgumentNotValidException() {
        // Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "field", "must not be null");
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleMethodArgumentNotValid(exception);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody().getMessage()).contains("Validation failed");
        assertThat(response.getBody().getMessage()).contains("must not be null");
    }

    @Test
    @DisplayName("Handle NoSuchElementException")
    public void testHandleNoSuchElementException() {
        // Arrange
        NoSuchElementException exception = new NoSuchElementException("No such element");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleNoSuchElementException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().getMessage()).isEqualTo("No such element");
    }

    @Test
    @DisplayName("Handle InternalException")
    public void testHandleInternalException() {
        // Arrange
        ExecutionControl.InternalException exception = new ExecutionControl.InternalException("Internal error");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleInternalException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody().getMessage()).isEqualTo("Internal error");
    }

    @Test
    @DisplayName("Handle ExternalMicroserviceException")
    public void testHandleExternalMicroserviceException() {
        // Arrange
        ExternalMicroserviceException exception = new ExternalMicroserviceException("External service error");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleExternalMicroserviceException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getBody().getMessage()).isEqualTo("External service error");
    }

    @Test
    @DisplayName("Handle UserNotFoundException")
    public void testHandleUserNotFoundException() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException("User not found");

        // Act
        Mono<ResponseEntity<ErrorResponse>> responseMono = exceptionHandler.handleUserNotFoundException(exception, webRequest);
        ResponseEntity<ErrorResponse> response = responseMono.block();

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getBody().getMessage()).isEqualTo("User not found");
    }
}
