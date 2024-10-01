package com.sociablesphere.usersociablesphere.api.dto;

import lombok.*;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String name;
    private String password;
    private String email;
}
