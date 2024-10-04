package com.sociablesphere.usersociablesphere.mapper;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.privacy.ApiTokenGenerator;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);

    public static Usuarios toUser(UserCreationDTO userCreationDTO) {
        logger.info("Mapping UserCreationDTO to User: {}", userCreationDTO.getEmail());
            Usuarios user = Usuarios.builder()
                .userName(userCreationDTO.getUserName())
                .name(userCreationDTO.getName())
                .lastName(userCreationDTO.getLastName())
                .email(userCreationDTO.getEmail())
                .photo(userCreationDTO.getPhoto())
                .description(userCreationDTO.getDescription())
                .password(PasswordUtil.hashPassword(userCreationDTO.getPassword()))
                .role(userCreationDTO.getRole())
                .wallet(0D)
                .apiToken(ApiTokenGenerator.generateRandomApiToken())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        logger.info("User mapped successfully: {}", user.getEmail());
        return user;
    }

    public static UserResponseDTO toUserResponseDTO(Usuarios user) {
        logger.info("Mapping User to UserResponseDTO for user: {}", user.getEmail());
        UserResponseDTO responseDTO = UserResponseDTO.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .description(user.getDescription())
                .role(user.getRole())
                .build();
        logger.info("UserResponseDTO mapped successfully for user: {}", responseDTO.getEmail());
        return responseDTO;
    }

    public static UserDetailDTO toUserDetailDTO(Usuarios user) {
        logger.info("Mapping User to UserDetailDTO for user: {}", user.getEmail());
        UserDetailDTO detailDTO = UserDetailDTO.builder()
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
        logger.info("UserDetailDTO mapped successfully for user: {}", detailDTO.getEmail());
        return detailDTO;
    }
}
