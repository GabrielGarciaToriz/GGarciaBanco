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
public class Cajero {

    private Integer idCajero;
    private String codigoCajero;
    private String ubicacion;
    private Boolean activo;
    private LocalDateTime fechaRegistro;

}
