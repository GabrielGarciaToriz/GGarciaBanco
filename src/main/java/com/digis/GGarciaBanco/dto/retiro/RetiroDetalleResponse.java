package com.digis.GGarciaBanco.dto.retiro;

import java.math.BigDecimal;
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
public class RetiroDetalleResponse {

    private Integer idRetiro;
    private Integer idDenominacion;

    private String tipo;
    private Long valorCentavos;
    private BigDecimal valor;

    private Integer cantidad;

    private Long subtotalCentavos;
    private BigDecimal subtotal;

}
