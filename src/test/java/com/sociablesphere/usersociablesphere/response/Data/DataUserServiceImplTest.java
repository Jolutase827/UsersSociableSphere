package com.sociablesphere.usersociablesphere.response.Data;


import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.model.Usuarios;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class DataUserServiceImplTest {

    public static final Long USER_ID = 1L;
    public static final String API_TOKEN = "validApiToken";

    public static final UserCreationDTO USER_CREATION_DTO = UserCreationDTO.builder()
            .userName("testUser")
            .name("Test")
            .lastName("User")
            .email("testuser@example.com")
            .photo("photo_url")
            .description("Test description")
            .password("Password1")
            .role("USER")
            .build();

    public static final UserDetailDTO USER_DETAIL_DTO = UserDetailDTO.builder()
            .id(USER_ID)
            .userName("testUser")
            .name("Test")
            .lastName("User")
            .email("testuser@example.com")
            .photo("photo_url")
            .description("Test description")
            .role("USER")
            .wallet(100.0)
            .apiToken(API_TOKEN)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    public static final UserLoginDTO USER_LOGIN_DTO = UserLoginDTO.builder()
            .name("Test")
            .password("Password1")
            .email("testuser@example.com")
            .build();

    public static final UserPasswordDTO USER_PASSWORD_DTO = UserPasswordDTO.builder()
            .oldPassword("Password1")
            .newPassword("NewPassword1")
            .build();

    public static final UserResponseDTO USER_RESPONSE_DTO = UserResponseDTO.builder()
            .id(USER_ID)
            .userName("testUser")
            .name("Test")
            .lastName("User")
            .email("testuser@example.com")
            .photo("photo_url")
            .description("Test description")
            .role("USER")
            .build();

    public static final List<Long> USER_IDS = Arrays.asList(1L, 2L, 3L);

    public static final Usuarios USUARIOS = Usuarios.builder()
            .id(USER_ID)
            .userName("testUser")
            .name("Test")
            .lastName("User")
            .email("testuser@example.com")
            .photo("photo_url")
            .description("Test description")
            .password("hashedPassword")
            .role("USER")
            .wallet(100.0)
            .apiToken(API_TOKEN)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
}