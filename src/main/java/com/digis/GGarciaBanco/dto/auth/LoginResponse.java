package com.digis.GGarciaBanco.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Integer idUsuario;
    private Integer idTarjeta;
    private String numeroTarjeta;
    private String nombreUsuario;
    private Integer idBanco;
    private String nombreBanco;
}
