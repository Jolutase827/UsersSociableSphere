package com.sociablesphere.usersociablesphere.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCreationDTO {
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private String password;
    private String role;
}
