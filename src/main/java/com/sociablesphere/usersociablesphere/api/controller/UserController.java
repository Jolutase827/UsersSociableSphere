package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/users")

public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<UserDetailDTO>> registerUser (@RequestBody UserCreationDTO userCreationDTO){
        Mono<UserDetailDTO> user = userService.register(userCreationDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Mono<UserDetailDTO>> loginUser (@RequestBody UserLoginDTO userLoginDTO){
        Mono<UserDetailDTO> user = userService.login(userLoginDTO);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Mono<UserDetailDTO>> patchPassword (@PathVariable UUID id, @RequestBody UserPasswordDTO userPassword){
        Mono<UserDetailDTO> user = userService.updatePassword(id, userPassword);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<UserDetailDTO>> updateUser (@PathVariable UUID id, @RequestBody UserCreationDTO dataToUpdate){
        Mono<UserDetailDTO> user = userService.updateUser(id,dataToUpdate);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{apiToken}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID apiToken) {
        userService.deleteAcount(apiToken);
        return ResponseEntity.noContent().build();
    }
}
