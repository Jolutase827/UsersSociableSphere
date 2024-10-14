package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.response.service.UserResponseService;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserResponseService userResponseService;

    public UserController(UserService userService, UserResponseService userResponseService) {
        this.userService = userService;
        this.userResponseService = userResponseService;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDetailDTO>> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        logger.info("Registering USER with EMAIL: {}", userCreationDTO.getEmail());
        return userService.register(userCreationDTO)
                .flatMap(user -> userResponseService.buildCreatedResponse(user, userCreationDTO.getEmail()))
                .doOnSuccess(user -> logger.info("User registered successfully with email: {}", userCreationDTO.getEmail()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("Login USER with EMAIL: {}", userLoginDTO.getEmail());
        return userService.login(userLoginDTO)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User logged in successfully with email: {}", userLoginDTO.getEmail()));
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword) {
        logger.info("Patching PASSWORD with ID: {}", id);
        return userService.updatePassword(id, userPassword)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Password updated successfully for user ID: {}", id));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate) {
        logger.info("Updating USER with ID: {}", id);
        return userService.updateUser(id, dataToUpdate)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User updated successfully for ID: {}", id));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        logger.info("Deleting USER with ID: {}", id);
        return userService.deleteAccount(id)
                .then(userResponseService.buildNoContentResponse())
                .doOnSuccess(v -> logger.info("User with ID: {} deleted successfully", id));
    }

    @GetMapping("/apiToken")
    public Mono<ResponseEntity<UserDetailDTO>> getUserByApiToken(@RequestParam String apiToken) {
        logger.info("Fetching a USER by ApiToken");
        return userService.findByApiToken(apiToken)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User found successfully with apiToken: {}", apiToken));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> getUserById(@PathVariable Long id) {
        logger.info("Fetching USER with ID: {}", id);
        return userService.findById(id)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User found successfully with ID: {}", id));
    }

    @PostMapping("/usersByIds")
    public Flux<ResponseEntity<UserResponseDTO>> getUsersByIds(@RequestBody List<Long> userIds) {
        logger.info("Fetching USERS by IDs: {}", userIds);
        return userService.findUsersByIds(userIds)
                .flatMap(userResponseService::buildUsersResponse)
                .doOnTerminate(() -> logger.info("USERS found successfully with IDs: {}", userIds));
    }

}
