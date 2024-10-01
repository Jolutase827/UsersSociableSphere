package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.*;
import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.usersociablesphere.exceptions.UserAlreadyExistsException;
import com.sociablesphere.usersociablesphere.exceptions.UserNotFoundException;
import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.model.users;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;


    @Override
    public Mono<UserDetailDTO> register(UserCreationDTO newUser) {
        return userRepository.findByEmail(newUser.getEmail())
                .hasElement()
                .flatMap(existsByEmail -> {
                    if (existsByEmail) {
                        return Mono.error(new UserAlreadyExistsException("Email is already in use."));
                    }
                    return userRepository.findByUserName(newUser.getUserName())
                            .hasElement();
                })
                .flatMap(existsByUserName -> {
                    if (existsByUserName) {
                        return Mono.error(new UserAlreadyExistsException("Username is already taken."));
                    }
                    return userRepository.save(UserMapper.toUser(newUser))
                            .map(UserMapper::toUserDetailDTO);
                });

    }

    @Override
    public Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify) {
        Mono<users> userMono = userRepository.findById(id)
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
    public Mono<Void> deleteAcount(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id " + id + " doesn't exist.")))
                .flatMap(user -> userRepository.deleteById(id));
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
        Mono<users> userMono = userRepository.findById(id)
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
