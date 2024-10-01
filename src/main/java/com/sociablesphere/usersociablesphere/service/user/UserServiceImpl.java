package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.usersociablesphere.exceptions.UserNotFoundException;
import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
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
        User user = UserMapper.toUser(newUser);
        return userRepository.save(user).map(UserMapper::toUserDetailDTO);
    }

    @Override
    public Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify) {
        Mono<User> userMono = userRepository.findById(id)
                                    .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id "+id+
                                                                                        " doesn't exists.")));;
        return userMono
                .filter(user -> PasswordUtil.checkPassword(dataToModify.getPassword(),user.getPassword()))
                .flatMap(user -> {
                    user.updateData(dataToModify);
                    return userRepository.save(user).map(UserMapper::toUserDetailDTO);
                });
    }


    @Override
    public void deleteAcount(Long id) {
        userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id " + id + " doesn't exist.")))
                .flatMap(user -> userRepository.deleteById(id))
                .subscribe();
    }

    @Override
    public Mono<UserDetailDTO> login(UserLoginDTO userToLogin) {
        return userRepository.findByEmail(userToLogin.getEmail())
                .switchIfEmpty(userRepository.findByUserName(userToLogin.getName()))
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid credentials.")))
                .filter(user -> PasswordUtil.checkPassword(userToLogin.getPassword(),user.getPassword()))
                .map(UserMapper::toUserDetailDTO);
    }

    @Override
    public Mono<UserDetailDTO> updatePassword(Long id, UserPasswordDTO passwordToUpdate) {
        Mono<User> userMono = userRepository.findById(id)
                                        .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id "+id+
                                                                                            " doesn't exists.")));
        return userMono
                .filter(user -> PasswordUtil.checkPassword(passwordToUpdate.getOldPassword(),user.getPassword()))
                .flatMap(user -> {
                    user.setPassword(PasswordUtil.hashPassword(passwordToUpdate.getNewPassword()));
                    return userRepository.save(user).map(UserMapper::toUserDetailDTO);
                });
    }

    @Override
    public Flux<UserDetailDTO> findAll() {
        return userRepository.findAll()
                .map(UserMapper::toUserDetailDTO);
    }
}
