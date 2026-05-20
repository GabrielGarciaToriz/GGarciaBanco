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
public class Tarjeta {

    private Integer idTarjeta;
    private String numeroTarjeta;
    private Integer idUsuario;
    private Usuario usuario;
    private Integer idBnaco;
    private Banco banco;
    private BigDecimal saldo;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaVencimiento;
}
