package com.sociablesphere.usersociablesphere.repository;
import com.sociablesphere.usersociablesphere.model.users;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends R2dbcRepository<users,Long> {

    public Mono<users> findByUserName(String userName);
    public Mono<users> findByEmail(String email);
}


