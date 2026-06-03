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
public class Denominacion {

    private Integer idDenominacion;
    private String tipo;
    private Long valorCentavos;
    private Boolean activo;

}
