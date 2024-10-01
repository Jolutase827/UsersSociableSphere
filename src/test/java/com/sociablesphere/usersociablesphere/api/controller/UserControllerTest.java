package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDetailDTO userDetailDTO;
    private UserCreationDTO userCreationDTO;
    private UserLoginDTO userLoginDTO;
    private UserPasswordDTO userPasswordDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID userId = UUID.randomUUID();

        userDetailDTO = UserDetailDTO.builder()
                .id(userId)
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role("USER")
                .build();

        userCreationDTO = UserCreationDTO.builder()
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .role("USER")
                .build();

        userLoginDTO = UserLoginDTO.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();

        userPasswordDTO = UserPasswordDTO.builder()
                .oldPassword("password123")
                .newPassword("newPassword123")
                .build();
    }

    @Test
    @DisplayName("Test getAllUsers returns all users")
    public void testGetAllUsers() {
        // Arrange
        when(userService.findAll()).thenReturn(Flux.just(userDetailDTO));

        // Act
        ResponseEntity<Flux<UserDetailDTO>> response = userController.getAllUsers();

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        StepVerifier.create(response.getBody())
                .expectNext(userDetailDTO)
                .verifyComplete();

        verify(userService, times(1)).findAll();
    }

    @Test
    @DisplayName("Test registerUser registers a new user")
    public void testRegisterUser() {
        // Arrange
        when(userService.register(any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act
        ResponseEntity<Mono<UserDetailDTO>> response = userController.registerUser(userCreationDTO);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        StepVerifier.create(response.getBody())
                .expectNext(userDetailDTO)
                .verifyComplete();

        verify(userService, times(1)).register(userCreationDTO);
    }

    @Test
    @DisplayName("Test loginUser logs in a user")
    public void testLoginUser() {
        // Arrange
        when(userService.login(any(UserLoginDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act
        ResponseEntity<Mono<UserDetailDTO>> response = userController.loginUser(userLoginDTO);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        StepVerifier.create(response.getBody())
                .expectNext(userDetailDTO)
                .verifyComplete();

        verify(userService, times(1)).login(userLoginDTO);
    }

    @Test
    @DisplayName("Test patchPassword updates user's password")
    public void testPatchPassword() {
        // Arrange
        UUID userId = userDetailDTO.getId();
        when(userService.updatePassword(eq(userId), any(UserPasswordDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act
        ResponseEntity<Mono<UserDetailDTO>> response = userController.patchPassword(userId, userPasswordDTO);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        StepVerifier.create(response.getBody())
                .expectNext(userDetailDTO)
                .verifyComplete();

        verify(userService, times(1)).updatePassword(userId, userPasswordDTO);
    }

    @Test
    @DisplayName("Test updateUser updates user information")
    public void testUpdateUser() {
        // Arrange
        UUID userId = userDetailDTO.getId();
        when(userService.updateUser(eq(userId), any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act
        ResponseEntity<Mono<UserDetailDTO>> response = userController.updateUser(userId, userCreationDTO);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        StepVerifier.create(response.getBody())
                .expectNext(userDetailDTO)
                .verifyComplete();

        verify(userService, times(1)).updateUser(userId, userCreationDTO);
    }

    @Test
    @DisplayName("Test deleteUser deletes a user account")
    public void testDeleteUser() {
        // Arrange
        UUID apiToken = UUID.randomUUID();
        doNothing().when(userService).deleteAcount(apiToken);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(apiToken);

        // Assert
        assertThat(response.getStatusCodeValue()).isEqualTo(204);
        verify(userService, times(1)).deleteAcount(apiToken);
    }
}
