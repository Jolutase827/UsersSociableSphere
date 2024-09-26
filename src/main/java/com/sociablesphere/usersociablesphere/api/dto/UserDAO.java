package com.sociablesphere.usersociablesphere.api.dto;

import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDAO {
    private UUID id;
    private String userName;
    private String name;
    private String lastName;
    private String email;
    private String photo;
    private String description;
    private String password;
    private String role;
    private Double wallet;
    private String apiToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
