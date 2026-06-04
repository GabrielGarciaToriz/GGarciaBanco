package com.digis.ggarciabanco.dto.cajero;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardCajeroResponse {

    private CajeroResponse cajero;
    private List<InventarioCajeroResponse> inventario;
}
