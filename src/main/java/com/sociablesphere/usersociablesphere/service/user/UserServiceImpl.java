package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserCreationDTO> register(UserCreationDTO newUser) {
        return null;
    }

    @Override
    public Mono<UserCreationDTO> updateUser(UserCreationDTO newUser) {
        return null;
    }

    @Override
    public void deleteAcount(UserCreationDTO newUser) {

    }
}
