package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public interface UserService {

    Mono<UserDetailDTO> register(UserCreationDTO newUser);
    Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify);
    Mono<Void> deleteAcount(Long id);
    Mono<UserDetailDTO> login(UserLoginDTO userToLogin);
    Mono<UserDetailDTO> updatePassword (Long id, UserPasswordDTO passwordToUpdate);
    Flux<UserDetailDTO> findAll();




}
