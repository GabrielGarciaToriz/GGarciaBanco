package com.digis.ggarciabanco.dto.retiro;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class RetiroResponse {

    private Integer idRetiro;
    private BigDecimal montoRetiro;
    private List<DetalleRetiroResponse> detalle;

    @Data
    public static class DetalleRetiroResponse {

        private Integer idDenominacion;
        private String tipo;
        private BigDecimal valorCentavos;
        private BigDecimal valor;
        private Integer cantidad;
        private BigDecimal subtotalCentavos;
        private BigDecimal subtotal;
    }

}
