package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.usersociablesphere.exceptions.UserAlreadyExistsException;
import com.sociablesphere.usersociablesphere.exceptions.UserNotFoundException;
import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import static com.sociablesphere.usersociablesphere.service.Data.DataUserServiceImplTest.*;

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
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.save(any())).thenReturn(Mono.just(userEntity));
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
                                .isEqualTo(userEntity);
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
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.empty());
            when(userRepository.findByUserName(USER_RETURN.getUserName())).thenReturn(Mono.just(userEntity));

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
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.just(userEntity));


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
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(anyLong())).thenReturn(Mono.just(userEntity));
            when(userRepository.save(any())).thenReturn(Mono.just(userEntity));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updatePassword(USER_ID, USER_PASSWORD_DTO);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(userEntity));
                    })
                    .verifyComplete();

            verify(userRepository).findById((anyLong()));
            verify(userRepository).save(any());
        }

        @Test
        @DisplayName("Given valid user ID and incorrect old password, Then throw InvalidCredentialsException")
        void updatePasswordInValid() {
            // Given
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(anyLong())).thenReturn(Mono.just(userEntity));

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
                            error.getMessage().equals("The user with the id " + USER_ID + " doesn't exists."))
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
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findAll()).thenReturn(Flux.just(userEntity));

            // When
            Flux<UserDetailDTO> userDetailDTOFlux = userService.findAll();

            // Then
            StepVerifier.create(userDetailDTOFlux)
                    .expectNextMatches(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(userEntity));
                        return true;
                    })
                    .verifyComplete();

            verify(userRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Login User")
    class LoginUser {
        @Test
        @DisplayName("Given valid email and password, Then return user details")
        void loginUserValid() {
            // Given
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.just(userEntity));
            when(userRepository.findByUserName(any())).thenReturn(Mono.just(userEntity));


            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.login(USER_LOGIN);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(userEntity));
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
    class UpdateUser {

        @Test
        @DisplayName("Given valid user ID and correct password, Then update the user details")
        void updateUserValid() {
            // Given
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findById(USER_ID)).thenReturn(Mono.just(userEntity));
            when(userRepository.save(any())).thenReturn(Mono.just(userEntity));

            // When
            Mono<UserDetailDTO> userDetailDTOMono = userService.updateUser(USER_ID, USER_RETURN);

            // Then
            StepVerifier.create(userDetailDTOMono)
                    .assertNext(userDetailDTO -> {
                        assertThat(userDetailDTO)
                                .isInstanceOf(UserDetailDTO.class)
                                .usingRecursiveComparison()
                                .isEqualTo(UserMapper.toUserDetailDTO(userEntity));
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
                            error.getMessage().equals("The user with the id " + USER_ID + " doesn't exists."))
                    .verify();

            verify(userRepository).findById(USER_ID);
            verify(userRepository, never()).save(any());
        }

        @Test
        @DisplayName("Given valid user ID but incorrect current password, Then do not update and return error")
        void updateUserInvalidPassword() {
            // Given
            User userEntity = UserMapper.toUser(USER_RETURN);


            when(userRepository.findById(USER_ID)).thenReturn(Mono.just(userEntity));

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


}