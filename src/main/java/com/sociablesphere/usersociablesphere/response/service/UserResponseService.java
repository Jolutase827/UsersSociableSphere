package com.sociablesphere.usersociablesphere.response.service;

import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import com.sociablesphere.usersociablesphere.api.dto.UserResponseDTO;
import com.sociablesphere.usersociablesphere.response.builder.UserResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserResponseService {

    public Mono<ResponseEntity<UserDetailDTO>> buildCreatedResponse(UserDetailDTO user, String email) {
        return Mono.fromCallable(() -> UserResponseBuilder.generateCreatedResponse(user, email));
    }

    public Mono<ResponseEntity<UserDetailDTO>> buildOkResponse(UserDetailDTO user) {
        return Mono.fromCallable(() -> UserResponseBuilder.generateOkResponse(user));
    }

    public Mono<ResponseEntity<Void>> buildNoContentResponse() {
        return Mono.just(UserResponseBuilder.generateNoContentResponse());
    }

    public Flux<ResponseEntity<UserResponseDTO>> buildUsersResponse(UserResponseDTO user) {
        return Flux.just(UserResponseBuilder.generateUserOkResponse(user));
    }


}
