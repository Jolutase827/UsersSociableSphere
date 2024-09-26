package com.sociablesphere.usersociablesphere.repository;
import java.util.UUID;
import com.sociablesphere.usersociablesphere.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository<User,UUID> {
    public Mono<User> findByUserName(String string);
    public Mono<User> findByEmail(String string);
}


