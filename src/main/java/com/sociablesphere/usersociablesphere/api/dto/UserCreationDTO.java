package com.sociablesphere.usersociablesphere.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserCreationDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "El nombre de usuario solo puede contener letras, números y guiones bajos")
    private String userName;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    private String name;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(max = 50, message = "El apellido no puede tener más de 50 caracteres")
    private String lastName;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @NotBlank(message = "La foto no puede estar vacía")
    private String photo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "La descripción no puede tener más de 250 caracteres")
    private String description;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula y un número")
    private String password;

    @NotBlank(message = "El rol no puede estar vacío")
    @Size(max = 20, message = "El rol no puede tener más de 20 caracteres")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "El rol debe ser 'USER' o 'ADMIN'")
    private String role;
}
