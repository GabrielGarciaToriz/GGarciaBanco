package com.digis.GGarciaBanco.entity;

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
    private String nombreCajero;
    private Boolean activo;
    private Banco banco;

}
