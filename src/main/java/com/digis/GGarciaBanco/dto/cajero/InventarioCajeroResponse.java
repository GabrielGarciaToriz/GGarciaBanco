package com.digis.GGarciaBanco.dto.cajero;

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
public class InventarioCajeroResponse {

    private Integer idCajero;
    private Integer idDenominacion;
    private String tipo;
    private Long valorCentavos;
    private BigDecimal valor;
    private Long cantidadActual;
    private Long totalCentavos;
    private BigDecimal total;
}
