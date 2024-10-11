package com.sociablesphere.usersociablesphere.response.builder;

import com.sociablesphere.usersociablesphere.api.dto.UserDetailDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserResponseBuilder {

    public static ResponseEntity<UserDetailDTO> generateCreatedResponse(UserDetailDTO user, String email) {
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public static ResponseEntity<Void> generateNoContentResponse() {
        return ResponseEntity.noContent().build();
    }

    public static ResponseEntity<UserDetailDTO> generateOkResponse(UserDetailDTO user) {
        return ResponseEntity.ok(user);
    }

}

