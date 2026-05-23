package com.digis.GGarciaBanco.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String numeroTarjeta;
    private String password;
}
