package com.sociablesphere.usersociablesphere.response.logic;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.response.service.UserResponseService;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceLogic {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceLogic.class);

    private final UserService userService;
    private final UserResponseService userResponseService;

    public Mono<ResponseEntity<UserDetailDTO>> registerUserAndBuildResponse(@Valid UserCreationDTO userCreationDTO) {
        logger.info("Attempting to register user with email: {}", userCreationDTO.getEmail());
        return userService.register(userCreationDTO)
                .flatMap(user -> userResponseService.buildCreatedResponse(user, userCreationDTO.getEmail()))
                .doOnSuccess(user -> logger.info("Successfully registered user with email: {}", userCreationDTO.getEmail()));
    }

    public Mono<ResponseEntity<UserDetailDTO>> loginUserAndBuildResponse(@Valid UserLoginDTO userLoginDTO) {
        logger.info("Attempting to log in user with email: {}", userLoginDTO.getEmail());
        return userService.login(userLoginDTO)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Successfully logged in user with email: {}", userLoginDTO.getEmail()));
    }

    public Mono<ResponseEntity<UserDetailDTO>> updatePasswordAndBuildResponse(Long id, @Valid UserPasswordDTO userPassword) {
        logger.info("Attempting to update password for user ID: {}", id);
        return userService.updatePassword(id, userPassword)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Successfully updated password for user ID: {}", id));
    }

    public Mono<ResponseEntity<UserDetailDTO>> updateUserAndBuildResponse(Long id, @Valid UserCreationDTO dataToUpdate) {
        logger.info("Attempting to update user with ID: {}", id);
        return userService.updateUser(id, dataToUpdate)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Successfully updated user with ID: {}", id));
    }

    public Mono<ResponseEntity<Void>> deleteUserAndBuildResponse(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        return userService.deleteAccount(id)
                .then(userResponseService.buildNoContentResponse())
                .doOnSuccess(v -> logger.info("Successfully deleted user with ID: {}", id));
    }

    public Mono<ResponseEntity<UserDetailDTO>> getUserByApiTokenAndBuildResponse(String apiToken) {
        logger.info("Attempting to fetch user by ApiToken: {}", apiToken);
        return userService.findByApiToken(apiToken)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Successfully fetched user by ApiToken: {}", apiToken));
    }

    public Mono<ResponseEntity<UserDetailDTO>> getUserByIdAndBuildResponse(Long id) {
        logger.info("Attempting to fetch user by ID: {}", id);
        return userService.findById(id)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Successfully fetched user by ID: {}", id));
    }

    public Flux<ResponseEntity<UserResponseDTO>> getUsersByIdsAndBuildResponse(List<Long> userIds) {
        logger.info("Attempting to fetch users by IDs: {}", userIds);
        return userService.findUsersByIds(userIds)
                .flatMap(userResponseService::buildUsersResponse)
                .doOnComplete(() -> logger.info("Successfully fetched users by IDs: {}", userIds));
    }
}
