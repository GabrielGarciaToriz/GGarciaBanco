package com.digis.ggarciabanco.dto.cajero;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class InventarioCajeroResponse {

    private Integer idCajero;
    private Integer idDenominacion;
    private String tipo;
    private BigDecimal valorCentavos;
    private BigDecimal valor;
    private Integer cantidadActual;
    private BigDecimal totalCentavos;
    private BigDecimal total;

}
