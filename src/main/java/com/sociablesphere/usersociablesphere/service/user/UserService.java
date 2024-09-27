package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public interface UserService {

    Mono<UserDetailDTO> register(UserCreationDTO newUser);
    Mono<UserDetailDTO> updateUser(UUID id, UserCreationDTO dataToModify);
    void deleteAcount(UUID id);
    Mono<UserDetailDTO> login(UserLoginDTO userToLogin);






}
