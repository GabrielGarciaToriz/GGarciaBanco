package com.digis.ggarciabanco.entity;

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

    private Cajero cajero;
    private Denominacion denominacion;
    private Long cantidadActual;

}
