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
public class Banco {

    private Integer idBanco;
    private String nombreBanco;
    private Boolean activo;
    private LocalDateTime fechaRegistro;
}
