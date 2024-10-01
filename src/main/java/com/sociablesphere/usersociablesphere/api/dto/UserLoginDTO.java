package com.sociablesphere.usersociablesphere.api.dto;

import lombok.*;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;
}
