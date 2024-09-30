package com.sociablesphere.usersociablesphere.service.Data;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;

import java.util.UUID;

public class DataUserServiceImplTest {

    public final static UUID USER_ID = UUID.randomUUID();

    public final static UserCreationDTO USER_RETURN = UserCreationDTO.builder()
            .userName("JoseLuis")
            .name("Jose")
            .lastName("Luis")
            .email("paco@example.com")
            .photo("/profile_picture/joseLuis.png")
            .description("Me gustan las peras!!!")
            .password(PasswordUtil.hashPassword("12345"))
            .role("normal")
            .build();

    public final static UserLoginDTO USER_LOGIN = UserLoginDTO.builder()
            .name("JoseLuis")
            .email("paco@example.com")
            .password("12345")
            .build();

    public final static UserResponseDTO USER_RESPONSE = UserResponseDTO.builder()
            .id(USER_ID)
            .userName("JoseLuis")
            .name("Jose")
            .lastName("Luis")
            .email("paco@example.com")
            .photo("/profile_picture/joseLuis.png")
            .description("Me gustan las peras!!!")
            .role("normal")
            .build();

    public final static UserPasswordDTO USER_PASSWORD_DTO = UserPasswordDTO.builder()
            .oldPassword("12345")  // Contraseña actual
            .newPassword("newPassword123")  // Nueva contraseña
            .build();

    public static final UserCreationDTO INVALID_PASSWORD_DTO = UserCreationDTO.builder()
            .userName("JoseLuis")
            .name("Jose")
            .lastName("Luis")
            .email("paco@example.com")
            .photo("/profile_picture/joseLuis.png")
            .description("Me gustan las peras!!!")
            .password("wrongPassword") // Contraseña incorrecta
            .role("normal")
            .build();
}
