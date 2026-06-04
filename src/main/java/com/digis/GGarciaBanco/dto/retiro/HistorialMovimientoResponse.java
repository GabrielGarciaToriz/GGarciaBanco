package com.digis.ggarciabanco.dto.retiro;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialMovimientoResponse {

    private Long identificador;
    private String registro;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String tarjetaUsada;
    private String nombreBanco;
    private Long idRetiro;
    private BigDecimal montoPesosSolicitado;
    private BigDecimal totalEntregado;
    private String fecha;
    private String estado;

}
