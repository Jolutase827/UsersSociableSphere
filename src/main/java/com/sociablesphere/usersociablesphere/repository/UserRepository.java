package com.sociablesphere.usersociablesphere.repository;
import com.sociablesphere.usersociablesphere.model.Usuarios;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UserRepository extends R2dbcRepository<Usuarios,Long> {

    public Mono<Usuarios> findByUserName(String userName);
    public Mono<Usuarios> findByEmail(String email);
    public Mono<Usuarios> findByApiToken(String apiToken);
    public Mono<Usuarios> findById(Long id);
    @Query("SELECT * FROM usuarios WHERE id IN (:ids)")
    Flux<Usuarios> findByIds(List<Long> ids);
}


