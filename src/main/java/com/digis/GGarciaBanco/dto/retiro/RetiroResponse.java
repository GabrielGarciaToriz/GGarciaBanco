package com.digis.GGarciaBanco.dto.retiro;

import java.util.List;
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
public class RetiroResponse {

    private Integer idRetiro;
    private String estado;
    private String mensaje;

    private List<RetiroDetalleResponse> detalle;
}
