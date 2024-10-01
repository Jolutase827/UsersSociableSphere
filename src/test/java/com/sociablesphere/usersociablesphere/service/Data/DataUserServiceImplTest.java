package com.sociablesphere.usersociablesphere.service.Data;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;

public class DataUserServiceImplTest {
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
}
