package com.sociablesphere.usersociablesphere.service.Data;

import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public class DataUserServiceImplTest {
    public final static Mono<User> USER_RETURN = Mono.just(User.builder()
            .id(UUID.randomUUID())
            .userName("JoseLuis")
            .name("Jose")
            .lastName("Luis")
            .email("paco@example.com")
            .photo("/profile_picture/joseLuis.png")
            .description("Me gustan las peras!!!")
            .password(PasswordUtil.hashPassword("12345"))
            .role("normal")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build());
}
