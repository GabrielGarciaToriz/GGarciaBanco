package com.digis.GGarciaBanco.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    private Integer idUsuario;
    private String publicId;
    private String nombre;
    private String apellidoPaterno;
    private String apeliidoMaterno;
    private String correo;

    private String password;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
}
