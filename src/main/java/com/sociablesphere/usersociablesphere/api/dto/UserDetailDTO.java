package com.sociablesphere.usersociablesphere.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO {

    @NotNull(message = "El ID no puede ser nulo")
    private UUID id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String userName;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    private String lastName;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La foto no puede estar vacía")
    private String photo;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "El rol no puede estar vacío")
    private String role;

    private Double wallet; // Puede ser nulo si no se establece.

    private String apiToken; // Puede ser nulo si no se establece.

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
