package com.sociablesphere.usersociablesphere.api.dto;

import lombok.*;

@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
