package com.digis.GGarciaBanco.dto.retiro;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class RetiroRequest {

    private Integer idUsuario;
    private String numeroTarjeta;
    private Integer idCajero;
    private BigDecimal monto;

}
