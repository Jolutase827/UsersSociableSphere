package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuariosMapperTest {

    private UserCreationDTO userCreationDTO;
    private Usuarios usuarios;
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

        usuarios = Usuarios.builder()
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
        Usuarios mappedUsuarios = UserMapper.toUser(userCreationDTO);

        // Assert
        assertThat(mappedUsuarios).isNotNull();
        assertThat(mappedUsuarios.getUserName()).isEqualTo(userCreationDTO.getUserName());
        assertThat(mappedUsuarios.getName()).isEqualTo(userCreationDTO.getName());
        assertThat(mappedUsuarios.getLastName()).isEqualTo(userCreationDTO.getLastName());
        assertThat(mappedUsuarios.getEmail()).isEqualTo(userCreationDTO.getEmail());
        assertThat(mappedUsuarios.getPhoto()).isEqualTo(userCreationDTO.getPhoto());
        assertThat(mappedUsuarios.getDescription()).isEqualTo(userCreationDTO.getDescription());
        assertThat(mappedUsuarios.getRole()).isEqualTo(userCreationDTO.getRole());

        assertThat(mappedUsuarios.getApiToken()).isNotNull();
        assertThat(mappedUsuarios.getApiToken()).isNotEmpty();
        assertThat(mappedUsuarios.getApiToken().length()).isLessThanOrEqualTo(255);

        assertThat(mappedUsuarios.getWallet()).isEqualTo(0.0);
        assertThat(mappedUsuarios.getCreatedAt()).isNotNull();
        assertThat(mappedUsuarios.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Test mapping from User to UserResponseDTO")
    public void testToUserResponseDTO() {
        // Act
        UserResponseDTO responseDTO = UserMapper.toUserResponseDTO(usuarios);

        // Assert
        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(usuarios.getId());
        assertThat(responseDTO.getUserName()).isEqualTo(usuarios.getUserName());
        assertThat(responseDTO.getName()).isEqualTo(usuarios.getName());
        assertThat(responseDTO.getLastName()).isEqualTo(usuarios.getLastName());
        assertThat(responseDTO.getEmail()).isEqualTo(usuarios.getEmail());
        assertThat(responseDTO.getPhoto()).isEqualTo(usuarios.getPhoto());
        assertThat(responseDTO.getDescription()).isEqualTo(usuarios.getDescription());
        assertThat(responseDTO.getRole()).isEqualTo(usuarios.getRole());
    }

    @Test
    @DisplayName("Test mapping from User to UserDetailDTO")
    public void testToUserDetailDTO() {
        // Act
        UserDetailDTO detailDTO = UserMapper.toUserDetailDTO(usuarios);

        // Assert
        assertThat(detailDTO).isNotNull();
        assertThat(detailDTO.getId()).isEqualTo(usuarios.getId());
        assertThat(detailDTO.getUserName()).isEqualTo(usuarios.getUserName());
        assertThat(detailDTO.getName()).isEqualTo(usuarios.getName());
        assertThat(detailDTO.getLastName()).isEqualTo(usuarios.getLastName());
        assertThat(detailDTO.getEmail()).isEqualTo(usuarios.getEmail());
        assertThat(detailDTO.getPhoto()).isEqualTo(usuarios.getPhoto());
        assertThat(detailDTO.getDescription()).isEqualTo(usuarios.getDescription());
        assertThat(detailDTO.getRole()).isEqualTo(usuarios.getRole());
        assertThat(detailDTO.getWallet()).isEqualTo(usuarios.getWallet());
        assertThat(detailDTO.getApiToken()).isEqualTo(usuarios.getApiToken());
        assertThat(detailDTO.getCreatedAt()).isEqualTo(usuarios.getCreatedAt());
        assertThat(detailDTO.getUpdatedAt()).isEqualTo(usuarios.getUpdatedAt());
    }
}
