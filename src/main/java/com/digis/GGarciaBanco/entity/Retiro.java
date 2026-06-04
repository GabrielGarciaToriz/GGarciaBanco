package com.digis.ggarciabanco.entity;

import java.math.BigDecimal;
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
public class Retiro {

    private Integer idRetiro;
    private Cajero cajero;
    private Tarjeta tarjeta;
    private Long montoCentavos;
    private LocalDateTime fecha;
    private String estado;
    private String mensaje;
}
