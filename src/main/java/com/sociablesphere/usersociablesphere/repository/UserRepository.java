package com.sociablesphere.usersociablesphere.repository;
import java.util.UUID;
import com.sociablesphere.usersociablesphere.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,UUID> {

    public Mono<User> findByUserName(String userName);
    public Mono<User> findByEmail(String email);
}


