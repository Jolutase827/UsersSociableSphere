package com.sociablesphere.usersociablesphere.api.controller;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        return userService.findAll();
    }

    @PostMapping("/create")
    public Mono<ResponseEntity<UserDetailDTO>> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO) {
        return userService.register(userCreationDTO)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(user));
    }


    @PostMapping("/login")
    public Mono<ResponseEntity<UserDetailDTO>> loginUser (@Valid @RequestBody UserLoginDTO userLoginDTO){
        return userService.login(userLoginDTO)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/password")
    public Mono<ResponseEntity<UserDetailDTO>> patchPassword (@PathVariable Long id, @Valid @RequestBody UserPasswordDTO userPassword){
        return userService.updatePassword(id, userPassword)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserDetailDTO>> updateUser (@PathVariable Long id, @Valid @RequestBody UserCreationDTO dataToUpdate){
        return  userService.updateUser(id,dataToUpdate)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable Long id) {
        return userService.deleteAcount(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
