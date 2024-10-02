package com.sociablesphere.usersociablesphere.model;

import com.sociablesphere.usersociablesphere.api.dto.UserCreationDTO;
import org.springframework.data.annotation.Id; // Usar el Id correcto para R2DBC, no JPA
import org.springframework.data.relational.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("users")
public class User {

    @Id
    private Long id;

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

    public void updateData(UserCreationDTO userCreationDTO){
        this.updatedAt = LocalDateTime.now();
        this.description = userCreationDTO.getDescription();
        this.email = userCreationDTO.getEmail();
        this.name = userCreationDTO.getName();
        this.role = userCreationDTO.getRole();
        this.userName = userCreationDTO.getUserName();
        this.photo = userCreationDTO.getPhoto();
        this.lastName = userCreationDTO.getLastName();
    }
}
