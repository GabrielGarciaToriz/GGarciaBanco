package com.digis.ggarciabanco.dto.login;

import lombok.Data;


@Data
public class LoginRequest {
    private String numeroTarjeta;
    private String password;
}
