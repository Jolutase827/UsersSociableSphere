package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

    @Test
    void testToUser() {
        // Given
        UserCreationDTO userCreationDTO = UserCreationDTO.builder()
                .userName("john_doe")
                .name("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .photo("photo_url")
                .description("A brief description")
                .password("securePassword1")
                .role("USER")
                .build();

        // When
        Usuarios user = UserMapper.toUser(userCreationDTO);

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getUserName()).isEqualTo("john_doe");
        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPhoto()).isEqualTo("photo_url");
        assertThat(user.getDescription()).isEqualTo("A brief description");
        assertThat(user.getPassword()).isNotNull();
        assertThat(user.getPassword()).isNotEqualTo("securePassword1");
        assertThat(user.getRole()).isEqualTo("USER");
    }

    @Test
    void testToUserResponseDTO() {
        // Given
        Usuarios user = new Usuarios();
        user.setUserName("john_doe");
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoto("photo_url");
        user.setDescription("A brief description");
        user.setRole("USER");

        // When
        UserResponseDTO userResponseDTO = UserMapper.toUserResponseDTO(user);

        // Then
        assertThat(userResponseDTO).isNotNull();
        assertThat(userResponseDTO.getUserName()).isEqualTo("john_doe");
        assertThat(userResponseDTO.getName()).isEqualTo("John");
        assertThat(userResponseDTO.getLastName()).isEqualTo("Doe");
        assertThat(userResponseDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userResponseDTO.getPhoto()).isEqualTo("photo_url");
        assertThat(userResponseDTO.getDescription()).isEqualTo("A brief description");
        assertThat(userResponseDTO.getRole()).isEqualTo("USER");
    }

    @Test
    void testToUserDetailDTO() {
        // Given
        Usuarios user = new Usuarios();
        user.setUserName("john_doe");
        user.setName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoto("photo_url");
        user.setDescription("A brief description");
        user.setRole("USER");

        // When
        UserDetailDTO userDetailDTO = UserMapper.toUserDetailDTO(user);

        // Then
        assertThat(userDetailDTO).isNotNull();
        assertThat(userDetailDTO.getUserName()).isEqualTo("john_doe");
        assertThat(userDetailDTO.getName()).isEqualTo("John");
        assertThat(userDetailDTO.getLastName()).isEqualTo("Doe");
        assertThat(userDetailDTO.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDetailDTO.getPhoto()).isEqualTo("photo_url");
        assertThat(userDetailDTO.getDescription()).isEqualTo("A brief description");
        assertThat(userDetailDTO.getRole()).isEqualTo("USER");
    }
}


