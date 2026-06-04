package com.digis.ggarciabanco.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegistroResponse {

    private Integer idUsuario;
    private String publicId;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String numeroTarjeta;
    private String nombreBanco;
    private Integer idBanco;
    private String bin;

}
