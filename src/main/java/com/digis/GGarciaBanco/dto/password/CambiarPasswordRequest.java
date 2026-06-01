package com.digis.GGarciaBanco.dto.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CambiarPasswordRequest(
        @NotBlank(message = "El token es obligatorio")
        String token,
        @NotBlank(message = "La nueva contrasena es obligatoria")
        @Size(min = 8, message = "La contrasena debe tener al menos 8 caracteres")
        String nuevaPassword
        ) {

}
