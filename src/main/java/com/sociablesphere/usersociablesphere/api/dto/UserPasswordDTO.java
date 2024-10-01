package com.sociablesphere.usersociablesphere.api.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


@Generated
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDTO {

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String oldPassword;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "La contraseña debe contener al menos una letra mayúscula, una letra minúscula y un número")
    private String newPassword;
}
