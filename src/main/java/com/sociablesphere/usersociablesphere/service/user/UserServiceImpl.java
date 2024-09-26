package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.model.User;
import com.sociablesphere.usersociablesphere.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> createUser(User user) {
        user.setApiToken(UUID.randomUUID().toString()); // O generar un token adecuado
        return userRepository.save(user);
    }

    @Override
    public Mono<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> updateUser(UUID id, User userDetails) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUserName(userDetails.getUserName());
                    existingUser.setName(userDetails.getName());
                    existingUser.setLastName(userDetails.getLastName());
                    existingUser.setEmail(userDetails.getEmail());
                    existingUser.setPhoto(userDetails.getPhoto());
                    existingUser.setDescription(userDetails.getDescription());
                    existingUser.setPassword(userDetails.getPassword());
                    existingUser.setRole(userDetails.getRole());
                    existingUser.setWallet(userDetails.getWallet());
                    existingUser.setApiToken(userDetails.getApiToken());

                    return userRepository.save(existingUser);
                });
    }

    @Override
    public Mono<Void> deleteUser(UUID id) {
        return userRepository.findById(id)
                .flatMap(existingUser -> userRepository.delete(existingUser))
                .then();
    }
}
