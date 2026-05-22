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
public class RetiroDetalle {

    private Integer idRetiro;
    private Retiro retiro;

    private Integer idDenominacion;
    private Denominacion deominacion;

    private Integer cantidad;
    private Long subtotalCentavos;

}
