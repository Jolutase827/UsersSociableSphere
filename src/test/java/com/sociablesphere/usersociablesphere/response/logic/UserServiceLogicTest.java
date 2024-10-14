package com.sociablesphere.usersociablesphere.response.logic;


import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.response.service.UserResponseService;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.sociablesphere.usersociablesphere.response.Data.DataUserServiceImplTest.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceLogicTest {

    @Mock
    private UserService userService;

    @Mock
    private UserResponseService userResponseService;

    @InjectMocks
    private UserServiceLogic userServiceLogic;

    @Nested
    @DisplayName("Register User and Build Response")
    class RegisterUserAndBuildResponse {

        @Test
        @DisplayName("Given a valid UserCreationDTO, Then register user and return CREATED response")
        void registerUserValid() {
            // Given
            UserCreationDTO userCreationDTO = USER_CREATION_DTO;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.status(201).body(userDetailDTO);

            when(userService.register(userCreationDTO)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildCreatedResponse(userDetailDTO, userCreationDTO.getEmail()))
                    .thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.registerUserAndBuildResponse(userCreationDTO);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(201);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).register(userCreationDTO);
            verify(userResponseService).buildCreatedResponse(userDetailDTO, userCreationDTO.getEmail());
        }
    }

    @Nested
    @DisplayName("Login User and Build Response")
    class LoginUserAndBuildResponse {

        @Test
        @DisplayName("Given a valid UserLoginDTO, Then login user and return OK response")
        void loginUserValid() {
            // Given
            UserLoginDTO userLoginDTO = USER_LOGIN_DTO;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.ok(userDetailDTO);

            when(userService.login(userLoginDTO)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.loginUserAndBuildResponse(userLoginDTO);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).login(userLoginDTO);
            verify(userResponseService).buildOkResponse(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Update Password and Build Response")
    class UpdatePasswordAndBuildResponse {

        @Test
        @DisplayName("Given a valid UserPasswordDTO, Then update password and return OK response")
        void updatePasswordValid() {
            // Given
            Long userId = USER_ID;
            UserPasswordDTO userPasswordDTO = USER_PASSWORD_DTO;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.ok(userDetailDTO);

            when(userService.updatePassword(userId, userPasswordDTO)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.updatePasswordAndBuildResponse(userId, userPasswordDTO);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).updatePassword(userId, userPasswordDTO);
            verify(userResponseService).buildOkResponse(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Update User and Build Response")
    class UpdateUserAndBuildResponse {

        @Test
        @DisplayName("Given a valid UserCreationDTO, Then update user and return OK response")
        void updateUserValid() {
            // Given
            Long userId = USER_ID;
            UserCreationDTO userCreationDTO = USER_CREATION_DTO;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.ok(userDetailDTO);

            when(userService.updateUser(userId, userCreationDTO)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.updateUserAndBuildResponse(userId, userCreationDTO);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).updateUser(userId, userCreationDTO);
            verify(userResponseService).buildOkResponse(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Delete User and Build Response")
    class DeleteUserAndBuildResponse {

        @Test
        @DisplayName("Given a valid user ID, Then delete user and return No Content response")
        void deleteUserValid() {
            // Given
            Long userId = USER_ID;
            ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();

            when(userService.deleteAccount(userId)).thenReturn(Mono.empty());
            when(userResponseService.buildNoContentResponse()).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<Void>> responseMono = userServiceLogic.deleteUserAndBuildResponse(userId);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(204);
                assertThat(response.getBody()).isNull();
            });

            verify(userService).deleteAccount(userId);
            verify(userResponseService).buildNoContentResponse();
        }
    }

    @Nested
    @DisplayName("Get User By ApiToken and Build Response")
    class GetUserByApiTokenAndBuildResponse {

        @Test
        @DisplayName("Given a valid ApiToken, Then return user details with OK response")
        void getUserByApiTokenValid() {
            // Given
            String apiToken = API_TOKEN;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.ok(userDetailDTO);

            when(userService.findByApiToken(apiToken)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.getUserByApiTokenAndBuildResponse(apiToken);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).findByApiToken(apiToken);
            verify(userResponseService).buildOkResponse(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Get User By ID and Build Response")
    class GetUserByIdAndBuildResponse {

        @Test
        @DisplayName("Given a valid user ID, Then return user details with OK response")
        void getUserByIdValid() {
            // Given
            Long userId = USER_ID;
            UserDetailDTO userDetailDTO = USER_DETAIL_DTO;
            ResponseEntity<UserDetailDTO> responseEntity = ResponseEntity.ok(userDetailDTO);

            when(userService.findById(userId)).thenReturn(Mono.just(userDetailDTO));
            when(userResponseService.buildOkResponse(userDetailDTO)).thenReturn(Mono.just(responseEntity));

            // When
            Mono<ResponseEntity<UserDetailDTO>> responseMono = userServiceLogic.getUserByIdAndBuildResponse(userId);

            // Then
            responseMono.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userDetailDTO);
            });

            verify(userService).findById(userId);
            verify(userResponseService).buildOkResponse(userDetailDTO);
        }
    }

    @Nested
    @DisplayName("Get Users By IDs and Build Response")
    class GetUsersByIdsAndBuildResponse {

        @Test
        @DisplayName("Given a list of user IDs, Then return user details with OK response")
        void getUsersByIdsValid() {
            // Given
            List<Long> userIds = USER_IDS;
            UserResponseDTO userResponseDTO = USER_RESPONSE_DTO;
            ResponseEntity<UserResponseDTO> responseEntity = ResponseEntity.ok(userResponseDTO);

            when(userService.findUsersByIds(userIds)).thenReturn(Flux.just(userResponseDTO));
            when(userResponseService.buildUsersResponse(userResponseDTO)).thenReturn(Flux.just(responseEntity));

            // When
            Flux<ResponseEntity<UserResponseDTO>> responseFlux = userServiceLogic.getUsersByIdsAndBuildResponse(userIds);

            // Then
            responseFlux.subscribe(response -> {
                assertThat(response.getStatusCodeValue()).isEqualTo(200);
                assertThat(response.getBody()).isEqualTo(userResponseDTO);
            });

            verify(userService).findUsersByIds(userIds);
            verify(userResponseService).buildUsersResponse(userResponseDTO);
        }
    }
}