package com.sociablesphere.usersociablesphere;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@Generated
public class UserSociableSphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserSociableSphereApplication.class, args);
	}

}
