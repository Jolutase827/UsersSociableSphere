package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private UserCreationDTO userCreationDTO;
    private User user;
    private String hashedPassword;

    @BeforeEach
    public void setUp() {
        userCreationDTO = UserCreationDTO.builder()
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A regular user")
                .password("password123")
                .role("USER")
                .build();

        hashedPassword = PasswordUtil.hashPassword(userCreationDTO.getPassword());

        user = User.builder()
                .id(Math.abs(new Random().nextLong()))
                .userName("jane_doe")
                .name("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .photo("photo_url")
                .description("Another regular user")
                .password("hashedPassword456")
                .role("ADMIN")
                .wallet(100.0)
                .apiToken("api_token_example")
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Test mapping from UserCreationDTO to User")
    public void testToUser() {
        // Act
        User mappedUser = UserMapper.toUser(userCreationDTO);

        // Assert
        assertThat(mappedUser).isNotNull();
        assertThat(mappedUser.getUserName()).isEqualTo(userCreationDTO.getUserName());
        assertThat(mappedUser.getName()).isEqualTo(userCreationDTO.getName());
        assertThat(mappedUser.getLastName()).isEqualTo(userCreationDTO.getLastName());
        assertThat(mappedUser.getEmail()).isEqualTo(userCreationDTO.getEmail());
        assertThat(mappedUser.getPhoto()).isEqualTo(userCreationDTO.getPhoto());
        assertThat(mappedUser.getDescription()).isEqualTo(userCreationDTO.getDescription());
        assertThat(mappedUser.getRole()).isEqualTo(userCreationDTO.getRole());

        assertThat(mappedUser.getApiToken()).isNotNull();
        assertThat(mappedUser.getApiToken()).isNotEmpty();
        assertThat(mappedUser.getApiToken().length()).isLessThanOrEqualTo(255);

        assertThat(mappedUser.getWallet()).isEqualTo(0.0);
        assertThat(mappedUser.getCreatedAt()).isNotNull();
        assertThat(mappedUser.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Test mapping from User to UserResponseDTO")
    public void testToUserResponseDTO() {
        // Act
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(user);

        // Assert
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(user.getId());
        assertThat(responseDTO.getUserName()).isEqualTo(user.getUserName());
        assertThat(responseDTO.getName()).isEqualTo(user.getName());
        assertThat(responseDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(responseDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(responseDTO.getPhoto()).isEqualTo(user.getPhoto());
        assertThat(responseDTO.getDescription()).isEqualTo(user.getDescription());
        assertThat(responseDTO.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("Test mapping from User to UserDetailDTO")
    public void testToUserDetailDTO() {
        // Act
        UserDetailDTO detailDTO = UserMapper.toUserDetailDTO(user);

        // Assert
        assertThat(detailDTO).isNotNull();
        assertThat(detailDTO.getId()).isEqualTo(user.getId());
        assertThat(detailDTO.getUserName()).isEqualTo(user.getUserName());
        assertThat(detailDTO.getName()).isEqualTo(user.getName());
        assertThat(detailDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(detailDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(detailDTO.getPhoto()).isEqualTo(user.getPhoto());
        assertThat(detailDTO.getDescription()).isEqualTo(user.getDescription());
        assertThat(detailDTO.getRole()).isEqualTo(user.getRole());
        assertThat(detailDTO.getWallet()).isEqualTo(user.getWallet());
        assertThat(detailDTO.getApiToken()).isEqualTo(user.getApiToken());
        assertThat(detailDTO.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(detailDTO.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }
}
