package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Mono<UserDetailDTO> updateUser(UserResponseDTO dataToUpdate) {
        return null;
    }

    @Override
    public void deleteAcount(UUID id) {

    }

    @Override
    public Mono<UserDetailDTO> login(UserLoginDTO userToLogin) {
        return null;
    }
}
