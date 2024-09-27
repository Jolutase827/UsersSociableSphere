package com.sociablesphere.usersociablesphere.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
