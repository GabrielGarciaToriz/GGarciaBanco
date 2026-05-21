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
    public Usuario usuario;
    public Banco banco;
    private String numeroTarjeta;
    private boolean activa;
    private LocalDateTime fecha_registro;

}
