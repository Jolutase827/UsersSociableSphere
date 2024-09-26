package com.sociablesphere.usersociablesphere.repository;

import com.sociablesphere.usersociablesphere.model.User;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface UserRepository extends R2dbcRepository<User,Long> {
}
