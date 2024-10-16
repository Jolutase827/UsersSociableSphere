package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.usersociablesphere.exceptions.UserAlreadyExistsException;
import com.sociablesphere.usersociablesphere.exceptions.UserNotFoundException;
import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import static com.sociablesphere.usersociablesphere.service.Data.DataUserServiceImplTest.*;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import reactor.core.publisher.Flux;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.lang.Long;
import java.util.List;
import java.util.Random;



@ExtendWith(MockitoExtension.class)
class userServiceImplTest {


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @Nested
    @DisplayName("Register user")
    class RegisterUsers {
        @Test
        @DisplayName("Given a valid user,Then sing up and return user details")
        void registerValidUser() {
            //Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.save(any())).thenReturn(Mono.just(usuarios));
            when(userRepository.findByEmail(any())).thenReturn(Mono.empty());
            when(userRepository.findByUserName(USER_RETURN.getUserName())).thenReturn(Mono.empty());


            //When
            Mono<UserDetailDTO> userDetailDTOMono = userService.register(USER_RETURN);

            //Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext( userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(usuarios);
                    })
                    .verifyComplete();
            verify(userRepository).save(any());
            verify(userRepository).findByUserName(USER_RETURN.getUserName());
            verify(userRepository).findByEmail(any());
        }

        @Test
        @DisplayName("Given a user with userName that already exists," +
                    " Then throw an UserAlreadyExistsException with username message")
        void registerErrorSameName() {
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.empty());
            when(userRepository.findByUserName(USER_RETURN.getUserName())).thenReturn(Mono.just(usuarios));

            //When
            Mono<UserDetailDTO> userDetailDTOMono = userService.register(USER_RETURN);

            //Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error ->
                            error instanceof UserAlreadyExistsException
                                    &&
                                    error.getMessage().equals("Username is already taken."))
                    .verify();

            verify(userRepository).findByUserName(USER_RETURN.getUserName());
            verify(userRepository).findByEmail(any());
        }

        @Test
        @DisplayName("Given a user with email that already exists," +
                " Then throw an UserAlreadyExistsException with email message")
        void registerErrorSameEmail() {
            //Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.just(usuarios));


            //When
            Mono<UserDetailDTO> userDetailDTOMono = userService.register(USER_RETURN);

            //Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error->
                            error instanceof UserAlreadyExistsException
                                    &&
                            error.getMessage().equals("Email is already in use."))
                    .verify();

            verify(userRepository).findByEmail(any());

        }
    }

    @Nested
    @DisplayName("Update Password")
    class UpdatePassword {
        @Test
        @DisplayName("Given valid user ID and correct old password, Then update the password")
        void updatePasswordValid() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(anyLong())).thenReturn(Mono.just(usuarios));
            when(userRepository.save(any())).thenReturn(Mono.just(usuarios));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updatePassword(USER_ID, USER_PASSWORD_DTO);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(usuarios));
                    })
                    .verifyComplete();

            verify(userRepository).findById((anyLong()));
            verify(userRepository).save(any());
        }

        @Test
        @DisplayName("Given valid user ID and incorrect old password, Then throw InvalidCredentialsException")
        void updatePasswordInValid() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(anyLong())).thenReturn(Mono.just(usuarios));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updatePassword(USER_ID, USER_WRONG_PASSWORD_DTO);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error -> error instanceof InvalidCredentialsException &&
                            error.getMessage().equals("Invalid credentials"))
                    .verify();

            verify(userRepository).findById(anyLong());
        }

        @Test
        @DisplayName("Given a non-existing user ID, Then throw UserNotFoundException")
        void updatePasswordUserNotFound() {
            // Given
            when(userRepository.findById(USER_ID)).thenReturn(Mono.empty());

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updatePassword(USER_ID, USER_PASSWORD_DTO);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error -> error instanceof UserNotFoundException &&
                            error.getMessage().equals("The user with the id " + USER_ID + " doesn't exist."))
                    .verify();

            verify(userRepository).findById(USER_ID);
        }
    }

    @Nested
    @DisplayName("Find All Users")
    class FindAllUsers {
        @Test
        @DisplayName("Find all users and return their details")
        void findAllUsers() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findAll()).thenReturn(Flux.just(usuarios));

            // When
            Flux<UserDetailDTO> userDetailDTOFlux = userService.findAll();

            // Then
            StepVerifier.create(userDetailDTOFlux)
                    .expectNextMatches(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(usuarios));
                        return true;
                    })
                    .verifyComplete();

            verify(userRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Login User")
    class LoginUsuarios {
        @Test
        @DisplayName("Given valid email and password, Then return user details")
        void loginUserValid() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.just(usuarios));
            when(userRepository.findByUserName(any())).thenReturn(Mono.just(usuarios));


            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.login(USER_LOGIN);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(usuarios));
                    })
                    .verifyComplete();

            verify(userRepository).findByEmail(any());
            verify(userRepository).findByUserName(any());

        }

        @Test
        @DisplayName("Given invalid email or password, Then throw InvalidCredentialsException")
        void loginUserInvalid() {
            // Given
            when(userRepository.findByEmail(any())).thenReturn(Mono.empty());
            when(userRepository.findByUserName(any())).thenReturn(Mono.empty());


            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.login(USER_LOGIN);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error -> error instanceof InvalidCredentialsException &&
                            error.getMessage().equals("Invalid credentials."))
                    .verify();

            verify(userRepository).findByEmail(any());
            verify(userRepository).findByUserName(any());
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUsuarios {

        @Test
        @DisplayName("Given valid user ID and correct password, Then update the user details")
        void updateUserValid() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(USER_ID)).thenReturn(Mono.just(usuarios));
            when(userRepository.save(any())).thenReturn(Mono.just(usuarios));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updateUser(USER_ID, USER_RETURN);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(usuarios));
                    })
                    .verifyComplete();

            verify(userRepository).findById(USER_ID);
            verify(userRepository).save(any());
        }

        @Test
        @DisplayName("Given a non-existing user ID, Then throw UserNotFoundException")
        void updateUserNotFound() {
            // Given
            when(userRepository.findById(USER_ID)).thenReturn(Mono.empty());

            // When

            // Then
            StepVerifier.create(userService.updateUser(USER_ID, USER_RETURN))
                    .expectErrorMatches(error -> error instanceof UserNotFoundException &&
                            error.getMessage().equals("The user with the id " + USER_ID + " doesn't exist."))
                    .verify();

            verify(userRepository).findById(USER_ID);
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("Given valid user ID but incorrect current password, Then do not update and return error")
        void updateUserInvalidPassword() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);


            when(userRepository.findById(USER_ID)).thenReturn(Mono.just(usuarios));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updateUser(USER_ID, INVALID_PASSWORD_DTO);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error -> error instanceof InvalidCredentialsException &&
                            error.getMessage().equals("Invalid credentials"))
                    .verify();

            verify(userRepository).findById(USER_ID);
            verify(userRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("Delete Account")
    class DeleteAccount {
        @Test
        @DisplayName("Given a valid user ID, Then delete the user successfully")
        void deleteAccountValid() {
            // Given
            Long userId = Long.valueOf(Long.toString(Math.abs(new Random().nextLong())));
            when(userRepository.findById(userId)).thenReturn(Mono.just(UserMapper.toUser(USER_RETURN)));
            when(userRepository.deleteById(userId)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = userService.deleteAccount(userId);

            // Then
            StepVerifier.create(result)
                    .verifyComplete();

            verify(userRepository).findById(userId);
            verify(userRepository).deleteById(userId);
        }

        @Test
        @DisplayName("Given a non-existing user ID, Then throw UserNotFoundException")
        void deleteAccountUserNotFound() {
            // Given
            Long userId = Long.valueOf(Long.toString(Math.abs(new Random().nextLong())));
            when(userRepository.findById(userId)).thenReturn(Mono.empty());

            // When
            Mono<Void> result = userService.deleteAccount(userId);

            // Then
            StepVerifier.create(result)
                    .expectErrorMatches(error -> error instanceof UserNotFoundException &&
                            error.getMessage().equals("The user with the id " + userId + " doesn't exist."))
                    .verify();

            verify(userRepository).findById(userId);
            verify(userRepository, never()).deleteById((Long) any());
        }
    }

    @Test
    @DisplayName("Test findByApiToken returns UserDetailDTO when valid token is provided")
    public void testFindByApiToken_Success() {

        String validApiToken = "validApiToken";
        Usuarios user = new Usuarios(1L, "user", "John", "Doe", "john.doe@example.com", null, null, "hashedPassword", "USER", null, validApiToken, null, null);

        when(userRepository.findByApiToken(validApiToken)).thenReturn(Mono.just(user));

        // Act
        Mono<UserDetailDTO> response = userService.findByApiToken(validApiToken);

        // Assert
        StepVerifier.create(response)
                .assertNext(result -> {
                    assertThat(result).isEqualTo(UserMapper.toUserDetailDTO(user));
                })
                .verifyComplete();

        verify(userRepository, times(1)).findByApiToken(validApiToken);
    }

    @Nested
    @DisplayName("Find User By ID")
    class FindUserById {

        @Test
        @DisplayName("Given a valid user ID, Then return user details")
        void findUserByIdValid() {
            // Given
            Usuarios usuarios = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(anyLong())).thenReturn(Mono.just(usuarios));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.findById(USER_ID);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(usuarios));
                    })
                    .verifyComplete();

            verify(userRepository).findById(USER_ID);
        }

        @Test
        @DisplayName("Given a non-existing user ID, Then throw UserNotFoundException")
        void findUserByIdNotFound() {
            // Given
            when(userRepository.findById(USER_ID)).thenReturn(Mono.empty());

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.findById(USER_ID);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error -> error instanceof UserNotFoundException &&
                            error.getMessage().equals("User with ID " + USER_ID + " not found"))
                    .verify();

            verify(userRepository).findById(USER_ID);
        }
    }



    @Test
    @DisplayName("Test findUsersByIds retrieves users correctly")
    void testFindUsersByIds() {
        // Given
        List<Long> userIds = Arrays.asList(1L, 2L, 3L);
        Usuarios user1 = new Usuarios(1L, "john_doe", "John", "Doe", "john.doe@example.com", null, null, "hashedPassword", "USER", null, null, null, null);
        Usuarios user2 = new Usuarios(2L, "jane_doe", "Jane", "Doe", "jane.doe@example.com", null, null, "hashedPassword", "USER", null, null, null, null);

        when(userRepository.findByIds(userIds)).thenReturn(Flux.just(user1, user2));

        // When
        Flux<UserResponseDTO> result = userService.findUsersByIds(userIds);

        // Then
        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getId().equals(1L) && dto.getUserName().equals("john_doe"))
                .expectNextMatches(dto -> dto.getId().equals(2L) && dto.getUserName().equals("jane_doe"))
                .verifyComplete();

        verify(userRepository).findByIds(userIds);
    }

}