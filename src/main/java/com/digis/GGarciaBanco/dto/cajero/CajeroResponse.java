    package com.digis.GGarciaBanco.dto.cajero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CajeroResponse {

    private Integer idCajero;
    private String nombreCajero;
    private Boolean activo;

    private Integer idBanco;
    private String nombreBanco;
}
