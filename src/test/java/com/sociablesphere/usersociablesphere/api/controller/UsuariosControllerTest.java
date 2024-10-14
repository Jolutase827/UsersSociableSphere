package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.response.logic.UserServiceLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserServiceLogic userServiceLogic;

    @InjectMocks
    private UserController userController;

    private UserCreationDTO userCreationDTO;
    private UserLoginDTO userLoginDTO;
    private UserPasswordDTO userPasswordDTO;
    private UserDetailDTO userDetailDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userCreationDTO = UserCreationDTO.builder()
                .email("test@example.com")
                .password("password123")
                .build();

        userLoginDTO = UserLoginDTO.builder()
                .email("login@example.com")
                .password("password123")
                .build();

        userPasswordDTO = UserPasswordDTO.builder()
                .oldPassword("oldPass")
                .newPassword("newPass")
                .build();

        userDetailDTO = UserDetailDTO.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .id(1L)
                .email("test@example.com")
                .build();
    }

    @Test
    void registerUser_shouldReturnCreatedResponse() {
        when(userServiceLogic.registerUserAndBuildResponse(any(UserCreationDTO.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> result = userController.registerUser(userCreationDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody() != null && response.getBody().getEmail().equals("test@example.com"))
                .verifyComplete();
    }


    @Test
    void loginUser_shouldReturnOkResponse() {
        when(userServiceLogic.loginUserAndBuildResponse(any(UserLoginDTO.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO))); // Usa el UserDetailDTO configurado

        Mono<ResponseEntity<UserDetailDTO>> result = userController.loginUser(userLoginDTO);

        StepVerifier.create(result)
                .expectNextMatches(response ->
                        response.getBody() != null &&
                                response.getBody().getEmail().equals("login@example.com")) // Asegúrate de que el email coincida
                .verifyComplete();
    }


    @Test
    void updatePassword_shouldReturnOkResponse() {
        when(userServiceLogic.updatePasswordAndBuildResponse(eq(1L), any(UserPasswordDTO.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> result = userController.patchPassword(1L, userPasswordDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody().getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void updateUser_shouldReturnOkResponse() {
        when(userServiceLogic.updateUserAndBuildResponse(eq(1L), any(UserCreationDTO.class)))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> result = userController.updateUser(1L, userCreationDTO);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody().getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void deleteUser_shouldReturnNoContentResponse() {
        when(userServiceLogic.deleteUserAndBuildResponse(1L))
                .thenReturn(Mono.just(ResponseEntity.noContent().build()));

        Mono<ResponseEntity<Void>> result = userController.deleteUser(1L);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getStatusCode().is2xxSuccessful())
                .verifyComplete();
    }

    @Test
    void getUserByApiToken_shouldReturnOkResponse() {
        when(userServiceLogic.getUserByApiTokenAndBuildResponse("token123"))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> result = userController.getUserByApiToken("token123");

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody().getEmail().equals("test@example.com"))
                .verifyComplete();
    }

    @Test
    void getUserById_shouldReturnOkResponse() {
        when(userServiceLogic.getUserByIdAndBuildResponse(1L))
                .thenReturn(Mono.just(ResponseEntity.ok(userDetailDTO)));

        Mono<ResponseEntity<UserDetailDTO>> result = userController.getUserById(1L);

        StepVerifier.create(result)
                .expectNextMatches(response -> response.getBody().getId().equals(1L))
                .verifyComplete();
    }

    @Test
    void getUsersByIds_shouldReturnOkResponse() {
        when(userServiceLogic.getUsersByIdsAndBuildResponse(any(List.class)))
                .thenReturn(Flux.just(ResponseEntity.ok(userResponseDTO)));

        Flux<ResponseEntity<UserResponseDTO>> result = userController.getUsersByIds(List.of(1L, 2L));

        StepVerifier.create(result)
                .expectNextCount(1)
                .verifyComplete();
    }
}
