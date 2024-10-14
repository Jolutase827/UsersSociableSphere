package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.response.logic.UserServiceLogic;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserServiceLogic userServiceLogic;

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDetailDTO>> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        return userServiceLogic.registerUserAndBuildResponse(userCreationDTO);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return userServiceLogic.loginUserAndBuildResponse(userLoginDTO);
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword(@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword) {
        return userServiceLogic.updatePasswordAndBuildResponse(id, userPassword);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate) {
        return userServiceLogic.updateUserAndBuildResponse(id, dataToUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userServiceLogic.deleteUserAndBuildResponse(id);
    }

    @GetMapping("/apiToken")
    public Mono<ResponseEntity<UserDetailDTO>> getUserByApiToken(@RequestParam String apiToken) {
        return userServiceLogic.getUserByApiTokenAndBuildResponse(apiToken);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> getUserById(@PathVariable Long id) {
        return userServiceLogic.getUserByIdAndBuildResponse(id);
    }

    @PostMapping("/usersByIds")
    public Flux<ResponseEntity<UserResponseDTO>> getUsersByIds(@RequestBody List<Long> userIds) {
        return userServiceLogic.getUsersByIdsAndBuildResponse(userIds);
    }
}
