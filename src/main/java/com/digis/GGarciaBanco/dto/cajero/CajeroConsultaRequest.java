package com.digis.ggarciabanco.dto.cajero;

import lombok.Data;

@Data
public class CajeroConsultaRequest {

    private Integer idUsuario;
    private String numeroTarjeta;

}
