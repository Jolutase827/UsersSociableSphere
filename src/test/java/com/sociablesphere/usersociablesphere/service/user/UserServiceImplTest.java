package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import static com.sociablesphere.usersociablesphere.service.Data.DataUserServiceImplTest.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {


    @MockBean
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
            when(userRepository.save(any())).thenReturn(Mono.just(UserMapper.toUser(USER_RETURN)));

            //When
            //Then
        }
    }

}