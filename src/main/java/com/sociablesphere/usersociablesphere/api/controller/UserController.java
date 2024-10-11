package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserPasswordDTO;
import com.sociablesphere.usersociablesphere.response.service.UserResponseService;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
        return userService.register(userCreationDTO)
                .flatMap(user -> userResponseService.buildCreatedResponse(user, userCreationDTO.getEmail()))
                .doOnSuccess(user -> logger.info("User registered successfully with email: {}", userCreationDTO.getEmail()));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User logged in successfully with email: {}", userLoginDTO.getEmail()));
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword) {
        return userService.updatePassword(id, userPassword)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("Password updated successfully for user ID: {}", id));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate) {
        return userService.updateUser(id, dataToUpdate)
                .flatMap(userResponseService::buildOkResponse)
                .doOnSuccess(user -> logger.info("User updated successfully for ID: {}", id));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteAccount(id)
                .then(userResponseService.buildNoContentResponse())
                .doOnSuccess(v -> logger.info("User with ID: {} deleted successfully", id));
    }
}
