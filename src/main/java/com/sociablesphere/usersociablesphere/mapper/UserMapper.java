package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserMapper {

    public User toUser(UserCreationDTO userCreationDTO) {
        return User.builder()
                .id(UUID.randomUUID())
                .userName(userCreationDTO.getUserName())
                .name(userCreationDTO.getName())
                .lastName(userCreationDTO.getLastName())
                .email(userCreationDTO.getEmail())
                .photo(userCreationDTO.getPhoto())
                .description(userCreationDTO.getDescription())
                .password(userCreationDTO.getPassword())
                .role(userCreationDTO.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .description(user.getDescription())
                .role(user.getRole())
                .build();
    }

    public UserDetailDTO toUserDetailDTO(User user) {
        return UserDetailDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .description(user.getDescription())
                .role(user.getRole())
                .wallet(user.getWallet())
                .apiToken(user.getApiToken())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}