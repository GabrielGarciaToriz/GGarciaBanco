package com.digis.ggarciabanco.dto.cajero;

import lombok.Data;

@Data
public class CajeroResponse {

    private Integer idCajero;
    private String nombreCajero;
    private Boolean activo;

    private Integer idBanco;
    private String nombreBanco;
}
