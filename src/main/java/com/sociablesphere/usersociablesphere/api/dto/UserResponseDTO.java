package com.sociablesphere.usersociablesphere.api.dto;

import lombok.*;

import java.util.UUID;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private UUID id;
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private String role;
}
