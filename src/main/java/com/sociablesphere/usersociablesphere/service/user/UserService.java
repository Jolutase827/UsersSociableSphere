package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserPasswordDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface UserService {

    Mono<UserDetailDTO> register(UserCreationDTO newUser);

    Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify);

    Mono<Void> deleteAccount(Long id);

    Mono<UserDetailDTO> login(UserLoginDTO userToLogin);

    Mono<UserDetailDTO> updatePassword(Long id, UserPasswordDTO passwordToUpdate);

    Flux<UserDetailDTO> findAll();

}
