package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.exceptions.UserAlreadyExistsException;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @Nested
    @DisplayName("Register user")
    class RegisterUser{
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
        }
        @Test
        @DisplayName("Given a user with userName that already exists," +
                    " Then throw an UserAlreadyExistsException with username message")
        void registerErrorSameName() {
            //Given
            User userEntity = UserMapper.toUser(USER_RETURN);
            when(userRepository.findByEmail(any())).thenReturn(Mono.empty());
            when(userRepository.findByUserName(USER_RETURN.getName())).thenReturn(Mono.just(userEntity));


            //When
            Mono<UserDetailDTO> userDetailDTOMono = userService.register(USER_RETURN);

            //Then
            StepVerifier.create(userDetailDTOMono)
                    .expectErrorMatches(error->
                            error instanceof UserAlreadyExistsException
                                    &&
                            error.getMessage().equals("Username is already taken."));
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
                            error.getMessage().equals("Email is already in use."));
        }
    }

}