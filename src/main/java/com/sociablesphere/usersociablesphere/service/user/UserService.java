package com.sociablesphere.usersociablesphere.service.user;

import com.sociablesphere.usersociablesphere.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<User> createUser(User user);
    Mono<User> getUserById(UUID id);
    Flux<User> getAllUsers();
    Mono<User> updateUser(UUID id, User userDetails);
    Mono<Void> deleteUser(UUID id);
}
