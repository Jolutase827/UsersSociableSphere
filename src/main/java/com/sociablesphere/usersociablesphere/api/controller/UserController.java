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
@RequestMapping("/api/v1/users")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Mono<UserDetailDTO>> registerUser (UserCreationDTO userCreationDTO){
        Mono<UserDetailDTO> user = userService.register(userCreationDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Mono<UserDetailDTO>> loginUser (UserLoginDTO userLoginDTO){
        Mono<UserDetailDTO> user = userService.login(userLoginDTO);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Mono<UserDetailDTO>> patchPassword (UserPasswordDTO userPassword){
        Mono<UserDetailDTO> user = userService.updatePassword(userPassword);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<UserDetailDTO>> updateUser (UserResponseDTO dataToUpdate){
        Mono<UserDetailDTO> user = userService.updateUser(dataToUpdate);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{apiToken}")
    public ResponseEntity<String> updateUser (UUID id){
        userService.deleteAcount(id);
        return ResponseEntity.status(OK).body("Order with id " + id + " has been deleted successfully");
    }

}
