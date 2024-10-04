package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserPasswordDTO;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserDetailDTO> getAllUsers() {
        logger.info("Fetching all users");
        Flux<UserDetailDTO> users = userService.findAll();
        return users
                .doOnComplete(() -> logger.info("Successfully fetched all users"))
                .doOnError(e -> logger.error("Error fetching users", e));
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDetailDTO>> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        logger.info("Attempting to register user with email: {}", userCreationDTO.getEmail());
        Mono<UserDetailDTO> user = userService.register(userCreationDTO);
        return user
                .map(ResponseEntity::ok)
                .doOnSuccess(u -> logger.info("User registered successfully with email: {}", userCreationDTO.getEmail()))
                .doOnError(e -> logger.error("Error registering user", e));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        logger.info("Attempting login for user with email: {}", userLoginDTO.getEmail());
        Mono<UserDetailDTO> user = userService.login(userLoginDTO);
        return user
                .map(ResponseEntity::ok)
                .doOnSuccess(u -> logger.info("User logged in successfully with email: {}", userLoginDTO.getEmail()))
                .doOnError(e -> logger.error("Error logging in user", e));
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword) {
        logger.info("Updating password for user ID: {}", id);
        Mono<UserDetailDTO> user = userService.updatePassword(id, userPassword);
        return user
                .map(ResponseEntity::ok)
                .doOnSuccess(u -> logger.info("Password updated successfully for user ID: {}", id))
                .doOnError(e -> logger.error("Error updating password for user ID: {}", id, e));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate) {
        logger.info("Updating user ID: {}", id);
        Mono<UserDetailDTO> user = userService.updateUser(id, dataToUpdate);
        return user
                .map(ResponseEntity::ok)
                .doOnSuccess(u -> logger.info("User updated successfully for ID: {}", id))
                .doOnError(e -> logger.error("Error updating user ID: {}", id, e));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        return userService.deleteAccount(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                .doOnSuccess(v -> logger.info("User with ID: {} deleted successfully", id))
                .doOnError(e -> logger.error("Error occurred while deleting user with ID: {}", id, e));
    }


}
