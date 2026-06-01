package com.digis.GGarciaBanco.dto.retiro;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class MovimientoResponse {
    private Integer idRetiro;
    private BigDecimal monto;
    private String numeroTarjeta;
    private String nombreCajero;
    private Date fecha;
}
