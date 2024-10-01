package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.UUID;
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserDetailDTO> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping("/create")
    public Mono<UserDetailDTO> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        return userService.register(userCreationDTO);
    }

    @PostMapping("/login")
    public Mono<UserDetailDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    @PatchMapping("/{id}/password")
    public Mono<UserDetailDTO> patchPassword(@PathVariable UUID id, @RequestBody UserPasswordDTO userPassword) {
        return userService.updatePassword(id, userPassword);
    }

    @PutMapping("/{id}")
    public Mono<UserDetailDTO> updateUser(@PathVariable UUID id, @RequestBody UserCreationDTO dataToUpdate) {
        return userService.updateUser(id, dataToUpdate);
    }

    @DeleteMapping("/{apiToken}")
    public Mono<Void> deleteUser(@PathVariable UUID apiToken) {
        return userService.deleteAcount(apiToken);
    }
}

