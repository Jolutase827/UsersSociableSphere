package com.sociablesphere.usersociablesphere.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {
    private Long id;
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private String role;
    private Double wallet;
    private String apiToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}