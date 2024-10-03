package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Flux<UserDetailDTO> getAllUsers() {
        Flux<UserDetailDTO> users = userService.findAll();
        return users;
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDetailDTO>> registerUser (@Valid @RequestBody UserCreationDTO userCreationDTO){
        Mono<UserDetailDTO> user = userService.register(userCreationDTO);
        return user.map(ResponseEntity::ok);
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser (@Valid @RequestBody UserLoginDTO userLoginDTO){
        Mono<UserDetailDTO> user = userService.login(userLoginDTO);
        return user.map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword (@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword){
        Mono<UserDetailDTO> user = userService.updatePassword(id, userPassword);
        return user.map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser (@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate){
        Mono<UserDetailDTO> user = userService.updateUser(id,dataToUpdate);
        return user.map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteAcount(id);
        return Mono.just(ResponseEntity.noContent().build());
    }
}
