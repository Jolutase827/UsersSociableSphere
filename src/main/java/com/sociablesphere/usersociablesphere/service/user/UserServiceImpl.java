package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    public Mono<UserDetailDTO> register(UserCreationDTO newUser) {
        return null;
    }

    @Override
    public Mono<UserDetailDTO> updateUser(UUID id, UserCreationDTO dataToModify) {
        return null;
    }


    @Override
    public void deleteAcount(UUID id) {}

    @Override
    public Mono<UserDetailDTO> login(UserLoginDTO userToLogin) {
        return null;
    }

    @Override
    public Mono<UserDetailDTO> updatePassword(UUID id, UserPasswordDTO passwordToUpdate) {
        return null;
    }

    @Override
    public Flux<UserDetailDTO> findAll() {
        return null;
    }
}
