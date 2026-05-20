package com.digis.GGarciaBanco.entity;

import java.math.BigDecimal;
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
    private BigDecimal valor;
    private String descripcion;
    private Boolean activa;

}
