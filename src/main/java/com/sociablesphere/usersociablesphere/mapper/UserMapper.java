package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.privacy.ApiTokenGenerator;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class UserMapper {

    public static User toUser(UserCreationDTO userCreationDTO) {
        return User.builder()
                .userName(userCreationDTO.getUserName())
                .name(userCreationDTO.getName())
                .lastName(userCreationDTO.getLastName())
                .email(userCreationDTO.getEmail())
                .photo(userCreationDTO.getPhoto())
                .description(userCreationDTO.getDescription())
                .password(PasswordUtil.hashPassword(userCreationDTO.getPassword()))
                .role(userCreationDTO.getRole())
                .wallet(0D)
                .apiToken(ApiTokenGenerator.generateRandomApiToken())  // Método para generar el token
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static UserResponseDTO toUserResponseDTO(User users) {
        return UserResponseDTO.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .name(users.getName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .photo(users.getPhoto())
                .description(users.getDescription())
                .role(users.getRole())
                .build();
    }

    public static UserDetailDTO toUserDetailDTO(User users) {
        return UserDetailDTO.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .name(users.getName())
                .lastName(users.getLastName())
                .email(users.getEmail())
                .photo(users.getPhoto())
                .description(users.getDescription())
                .role(users.getRole())
                .wallet(users.getWallet())
                .apiToken(users.getApiToken())
                .createdAt(users.getCreatedAt())
                .updatedAt(users.getUpdatedAt())
                .build();
    }
}