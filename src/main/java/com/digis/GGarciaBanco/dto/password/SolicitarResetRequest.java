package com.digis.GGarciaBanco.dto.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SolicitarResetRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "Formato de correo invalido")
        String correo
        ) {

}
