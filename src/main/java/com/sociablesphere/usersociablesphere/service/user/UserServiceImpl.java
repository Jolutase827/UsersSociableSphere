package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserLoginDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserPasswordDTO;
import com.sociablesphere.usersociablesphere.exceptions.InvalidCredentialsException;
import com.sociablesphere.usersociablesphere.exceptions.UserAlreadyExistsException;
import com.sociablesphere.usersociablesphere.exceptions.UserNotFoundException;
import com.sociablesphere.usersociablesphere.mapper.UserMapper;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import com.sociablesphere.usersociablesphere.privacy.PasswordUtil;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Override
    public Mono<UserDetailDTO> register(UserCreationDTO newUser) {
        logger.info("Registering new user with email: {}", newUser.getEmail());
        return userRepository.findByEmail(newUser.getEmail())
                .hasElement()
                .flatMap(existsByEmail -> {
                    if (existsByEmail) {
                        logger.error("Email {} is already in use.", newUser.getEmail());
                        return Mono.error(new UserAlreadyExistsException("Email is already in use."));
                    }
                    return userRepository.findByUserName(newUser.getUserName())
                            .hasElement();
                })
                .flatMap(existsByUserName -> {
                    if (existsByUserName) {
                        logger.error("Username {} is already taken.", newUser.getUserName());
                        return Mono.error(new UserAlreadyExistsException("Username is already taken."));
                    }
                    return userRepository.save(UserMapper.toUser(newUser))
                            .doOnSuccess(user -> logger.info("User registered successfully: {}", user.getId()))
                            .map(UserMapper::toUserDetailDTO);
                });
    }

    @Override
    public Mono<UserDetailDTO> updateUser(Long id, UserCreationDTO dataToModify) {
        logger.info("Updating user with ID: {}", id);
        Mono<Usuarios> userMono = userRepository.findById(id);

        return userMono
                .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id " + id + " doesn't exist.")))
                .filter(user -> PasswordUtil.checkPassword(dataToModify.getPassword(), user.getPassword()))
                .flatMap(user -> {
                    user.updateData(dataToModify);
                    return userRepository.save(user)
                            .doOnSuccess(updatedUser -> logger.info("User updated successfully: {}", updatedUser.getId()))
                            .map(UserMapper::toUserDetailDTO);
                })
                .doOnError(e -> logger.error("Error updating user with ID: {}", id, e));
    }

    @Override
    public Mono<Void> deleteAccount(Long id) {
        logger.info("Attempting to delete user with ID: {}", id);
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id " + id + " doesn't exist.")))
                .flatMap(user -> userRepository.deleteById(id)
                        .doOnSuccess(v -> logger.info("User with ID: {} deleted successfully", id))
                        .doOnError(e -> logger.error("Error occurred while deleting user with ID: {}", id, e))
                );
    }


    @Override
    public Mono<UserDetailDTO> login(UserLoginDTO userToLogin) {
        logger.info("Logging in user with email or username: {}", userToLogin.getEmail());
        return userRepository.findByEmail(userToLogin.getEmail())
                .switchIfEmpty(userRepository.findByUserName(userToLogin.getName()))
                .switchIfEmpty(Mono.error(new InvalidCredentialsException("Invalid credentials.")))
                .filter(user -> PasswordUtil.checkPassword(userToLogin.getPassword(), user.getPassword()))
                .doOnSuccess(user -> logger.info("User logged in successfully: {}", user.getId()))
                .map(UserMapper::toUserDetailDTO)
                .doOnError(e -> logger.error("Login failed for user: {}", userToLogin.getEmail(), e));
    }

    @Override
    public Mono<UserDetailDTO> updatePassword(Long id, UserPasswordDTO passwordToUpdate) {
        logger.info("Updating password for user with ID: {}", id);
        Mono<Usuarios> userMono = userRepository.findById(id);

        return userMono
                .switchIfEmpty(Mono.error(new UserNotFoundException("The user with the id " + id + " doesn't exist.")))
                .filter(user -> PasswordUtil.checkPassword(passwordToUpdate.getOldPassword(), user.getPassword()))
                .flatMap(user -> {
                    user.setPassword(PasswordUtil.hashPassword(passwordToUpdate.getNewPassword()));
                    return userRepository.save(user)
                            .doOnSuccess(updatedUser -> logger.info("Password updated successfully for user ID: {}", id))
                            .map(UserMapper::toUserDetailDTO);
                })
                .doOnError(e -> logger.error("Error updating password for user ID: {}", id, e));
    }

    @Override
    public Flux<UserDetailDTO> findAll() {
        logger.info("Fetching all users");
        return userRepository.findAll()
                .doOnComplete(() -> logger.info("Fetched all users successfully"))
                .doOnError(e -> logger.error("Error fetching users", e))
                .map(UserMapper::toUserDetailDTO);
    }
}
