package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserCreationDTO> register(UserCreationDTO newUser);
    Mono<UserCreationDTO> updateUser(UserCreationDTO newUser);
    void deleteAcount(UserCreationDTO newUser);





}
