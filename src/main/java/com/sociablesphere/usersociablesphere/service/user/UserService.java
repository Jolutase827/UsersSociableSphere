package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface UserService {

    Mono<UserDetailDTO> register(UserCreationDTO newUser);

    Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify);

    Mono<Void> deleteAccount(Long id);

    Mono<UserDetailDTO> login(UserLoginDTO userToLogin);

    Mono<UserDetailDTO> updatePassword(Long id, UserPasswordDTO passwordToUpdate);

    Flux<UserDetailDTO> findAll();

    Mono<UserDetailDTO> findByApiToken(String apiToken);
    Mono<UserDetailDTO> findById(Long id);
    Flux<UserResponseDTO> findUsersByIds(List<Long> userIds);



}
