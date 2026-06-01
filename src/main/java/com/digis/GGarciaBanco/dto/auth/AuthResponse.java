package com.digis.GGarciaBanco.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private String nombreUsuario;
    private String numeroTarjeta;
    private Long idUsuario;
    private String nombreBanco;
    private String public_id;
}
