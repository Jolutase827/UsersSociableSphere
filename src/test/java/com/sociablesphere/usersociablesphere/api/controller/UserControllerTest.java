package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    private WebTestClient webTestClient;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        webTestClient = WebTestClient.bindToController(userController).build();
    }

    @Test
    public void testGetAllUsers() {
        // Arrange
        UserDetailDTO user1 = UserDetailDTO.builder()
                .id(UUID.randomUUID())
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A user")
                .role("USER")
                .wallet(100.0)
                .apiToken("token123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Flux<UserDetailDTO> users = Flux.just(user1);

        when(userService.findAll()).thenReturn(users);

        // Act & Assert
        webTestClient.get().uri("/v1/users")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDetailDTO.class)
                .hasSize(1)
                .consumeWith(response -> {
                    UserDetailDTO responseBody = response.getResponseBody().get(0);
                    assert responseBody.getUserName().equals("john_doe");
                });

        verify(userService, times(1)).findAll();
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A user")
                .password("password123")
                .role("USER")
                .build();

        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
                .id(UUID.randomUUID())
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A user")
                .role("USER")
                .wallet(100.0)
                .apiToken("token123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userService.register(any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act & Assert
        webTestClient.post().uri("/v1/users/create")
                .contentType(APPLICATION_JSON)
                .bodyValue(userCreationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailDTO.class)
                .consumeWith(response -> {
                    UserDetailDTO responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.getUserName().equals("john_doe");
                });

        verify(userService, times(1)).register(any(UserCreationDTO.class));
    }

    @Test
    public void testLoginUser() {
        // Arrange
        UserLoginDTO userLoginDTO = UserLoginDTO.builder()
                .name("John")
                .password("password123")
                .email("john.doe@example.com")
                .build();

        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
                .id(UUID.randomUUID())
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A user")
                .role("USER")
                .wallet(100.0)
                .apiToken("token123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userService.login(any(UserLoginDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act & Assert
        webTestClient.post().uri("/v1/users/login")
                .contentType(APPLICATION_JSON)
                .bodyValue(userLoginDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailDTO.class)
                .consumeWith(response -> {
                    UserDetailDTO responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.getEmail().equals("john.doe@example.com");
                });

        verify(userService, times(1)).login(any(UserLoginDTO.class));
    }

    @Test
    public void testPatchPassword() {
        // Arrange
        UUID userId = UUID.randomUUID();

        UserPasswordDTO userPasswordDTO = UserPasswordDTO.builder()
                .oldPassword("oldpassword")
                .newPassword("newpassword")
                .build();

        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
                .id(userId)
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A user")
                .role("USER")
                .wallet(100.0)
                .apiToken("token123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userService.updatePassword(eq(userId), any(UserPasswordDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act & Assert
        webTestClient.patch().uri("/v1/users/{id}/password", userId)
                .contentType(APPLICATION_JSON)
                .bodyValue(userPasswordDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailDTO.class)
                .consumeWith(response -> {
                    UserDetailDTO responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.getId().equals(userId);
                });

        verify(userService, times(1)).updatePassword(eq(userId), any(UserPasswordDTO.class));
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        UUID userId = UUID.randomUUID();

        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .userName("john_doe_updated")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("new_photo_url")
                .description("An updated user")
                .password("password123")
                .role("USER")
                .build();

        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
                .id(userId)
                .userName("john_doe_updated")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("new_photo_url")
                .description("An updated user")
                .role("USER")
                .wallet(100.0)
                .apiToken("token123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(userService.updateUser(eq(userId), any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));

        // Act & Assert
        webTestClient.put().uri("/v1/users/{id}", userId)
                .contentType(APPLICATION_JSON)
                .bodyValue(userCreationDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDetailDTO.class)
                .consumeWith(response -> {
                    UserDetailDTO responseBody = response.getResponseBody();
                    assert responseBody != null;
                    assert responseBody.getUserName().equals("john_doe_updated");
                });

        verify(userService, times(1)).updateUser(eq(userId), any(UserCreationDTO.class));
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        UUID apiToken = UUID.randomUUID();

        doNothing().when(userService).deleteAcount(apiToken);

        // Act & Assert
        webTestClient.delete().uri("/v1/users/{apiToken}", apiToken)
                .exchange()
                .expectStatus().isNoContent();

        verify(userService, times(1)).deleteAcount(apiToken);
    }
}
