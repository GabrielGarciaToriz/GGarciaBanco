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
public class CajeroInventario {

    private Integer idCajeroInventario;

    private Integer idCajero;
    private Cajero cajero;

    private Integer idDenominacion;
    private Denominacion denominacion;

    private Integer cantidad;
    private LocalDateTime fechaActualizacion;

}
