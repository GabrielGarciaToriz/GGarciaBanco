package com.digis.GGarciaBanco.entity;

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
    private String folio;

    private Integer idUsuario;
    private Usuario usuario;

    private Integer idTarjeta;
    private Tarjeta tarjeta;

    private Integer idCajero;
    private Cajero cajero;

    private BigDecimal monto;
    private LocalDateTime fechaRetiro;

    private Boolean exitoso;
    private String mensaje;
}
