package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserPasswordDTO;
import com.sociablesphere.usersociablesphere.response.service.UserResponseService;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserResponseService userResponseService;

    @InjectMocks
    private UserController userController;

    private UserDetailDTO userDetailDTO;
    private UserCreationDTO userCreationDTO;
    private UserLoginDTO userLoginDTO;
    private UserPasswordDTO userPasswordDTO;

    @BeforeEach
    void setUp() {
        Long userId = new Random().nextLong();

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
    @DisplayName("Test registerUser registers a new user")
    void testRegisterUser() {
        when(userService.register(any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildCreatedResponse(eq(userDetailDTO), eq(userCreationDTO.getEmail())))
                .thenReturn(Mono.just(ResponseEntity.status(HttpStatus.CREATED).body(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.registerUser(userCreationDTO);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService).register(userCreationDTO);
        verify(userResponseService).buildCreatedResponse(userDetailDTO, userCreationDTO.getEmail());
    }

    @Test
    @DisplayName("Test loginUser logs in a user")
    void testLoginUser() {
        when(userService.login(any(UserLoginDTO.class))).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.loginUser(userLoginDTO);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService).login(userLoginDTO);
        verify(userResponseService).buildOkResponse(userDetailDTO);
    }

    @Test
    @DisplayName("Test patchPassword updates user's password")
    void testPatchPassword() {
        Long userId = userDetailDTO.getId();
        when(userService.updatePassword(eq(userId), any(UserPasswordDTO.class))).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.patchPassword(userId, userPasswordDTO);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService).updatePassword(userId, userPasswordDTO);
        verify(userResponseService).buildOkResponse(userDetailDTO);
    }

    @Test
    @DisplayName("Test updateUser updates user information")
    void testUpdateUser() {
        Long userId = userDetailDTO.getId();
        when(userService.updateUser(eq(userId), any(UserCreationDTO.class))).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.updateUser(userId, userCreationDTO);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService).updateUser(userId, userCreationDTO);
        verify(userResponseService).buildOkResponse(userDetailDTO);
    }

    @Test
    @DisplayName("Test deleteUser deletes a user account")
    void testDeleteUser() {
        Long id = Math.abs(new Random().nextLong());
        when(userService.deleteAccount(id)).thenReturn(Mono.empty());
        when(userResponseService.buildNoContentResponse()).thenReturn(Mono.just(ResponseEntity.noContent().build()));

        Mono<ResponseEntity<Void>> response = userController.deleteUser(id);

        StepVerifier.create(response)
                .assertNext(result -> assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT))
                .verifyComplete();

        verify(userService).deleteAccount(id);
        verify(userResponseService).buildNoContentResponse();
    }

    @Test
    @DisplayName("Test find user by apiToken")
    public void testFindByApiToken() {
        String validApiToken = "validApiToken";

        when(userService.findByApiToken(validApiToken)).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.getUserByApiToken(validApiToken);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCodeValue()).isEqualTo(200);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService, times(1)).findByApiToken(validApiToken);
        verify(userResponseService).buildOkResponse(userDetailDTO);
    }

    @Test
    @DisplayName("Test find user by ID")
    public void testGetUserById() {
        Long userId = userDetailDTO.getId();

        when(userService.findById(userId)).thenReturn(Mono.just(userDetailDTO));
        when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> response = userController.getUserById(userId);

        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result.getStatusCodeValue()).isEqualTo(200);
                    assertThat(result.getBody()).isEqualTo(userDetailDTO);
                })
                .verifyComplete();

        verify(userService, times(1)).findById(userId);
        verify(userResponseService).buildOkResponse(userDetailDTO);
    }

    @Test
    @DisplayName("Test getUsersByIds returns users by their IDs")
    void testGetUsersByIds() {
        // Given
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);
        UserResponseDTO user1 = new UserResponseDTO(1L, "john_doe", "John", "Doe", "john.doe@example.com", null, null, "USER");
        UserResponseDTO user2 = new UserResponseDTO(2L, "jane_doe", "Jane", "Doe", "jane.doe@example.com", null, null, "USER");

        // Mock the service to return a Flux of users
        when(userService.findUsersByIds(userIds)).thenReturn(Flux.just(user1, user2));

        // When
        Mono<ResponseEntity<Flux<UserResponseDTO>>> response = userController.getUsersByIds(userIds);

        // Then
        StepVerifier.create(response)
                .assertNext(responseEntity -> {
                    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

                    // Verify that the body contains the correct Flux of users
                    Flux<UserResponseDTO> userFlux = responseEntity.getBody();
                    StepVerifier.create(userFlux)
                            .expectNext(user1)
                            .expectNext(user2)
                            .verifyComplete();
                })
                .verifyComplete();

        // Verify that the service was called with the correct arguments
        verify(userService).findUsersByIds(userIds);
    }


}
