package com.sociablesphere.usersociablesphere.model;

import com.sociablesphere.usersociablesphere.api.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "photo")
    private String photo;

    @Column(name = "description")
    private String description;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "wallet")
    private Double wallet;

    @Column(name = "api_token", unique = true)
    private String apiToken;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static UserDTO fromEntity(User user){
        return UserDTO.builder()
                .id(user.id)
                .userName(user.userName)
                .name(user.name)
                .lastName(user.lastName)
                .email(user.email)
                .photo(user.photo)
                .description(user.description)
                .role(user.role)
                .wallet(user.wallet)
                .apiToken(user.apiToken)
                .build();
    }
}
