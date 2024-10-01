package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.users;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class UserMapper {

    public static users toUser(UserCreationDTO userCreationDTO) {
        return users.builder()
                .userName(userCreationDTO.getUserName())
                .name(userCreationDTO.getName())
                .lastName(userCreationDTO.getLastName())
                .email(userCreationDTO.getEmail())
                .photo(userCreationDTO.getPhoto())
                .description(userCreationDTO.getDescription())
                .password(PasswordUtil.hashPassword(userCreationDTO.getPassword()))
                .role(userCreationDTO.getRole())
                .wallet(0D)
                .apiToken(generateRandomApiToken())  // Método para generar el token
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    private static String generateRandomApiToken() {
        long randomLong = Math.abs(new Random().nextLong());
        String apiToken = Long.toString(randomLong);

        return apiToken.length() <= 255 ? apiToken : apiToken.substring(0, 255);
    }

    public static UserResponseDTO toUserResponseDTO(users users) {
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

    public static UserDetailDTO toUserDetailDTO(users users) {
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