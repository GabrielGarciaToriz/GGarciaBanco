package com.digis.ggarciabanco.service;

import com.digis.ggarciabanco.dto.Result;
import com.digis.ggarciabanco.dto.cajero.CajeroResponse;
import com.digis.ggarciabanco.dto.cajero.DashboardCajeroResponse;
import com.digis.ggarciabanco.dto.cajero.InventarioCajeroResponse;
import com.digis.ggarciabanco.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService extends BaseService {

    @Autowired
    private CajeroRepository cajeroRepository;

    // --- NUEVO MÉTODO PARA EL DASHBOARD ---
    public Result<DashboardCajeroResponse> obtenerDashboardCajero(Integer idUsuario, String numeroTarjeta) {
        // Usamos tu método 'ejecutar()' heredado de BaseService
        return ejecutar(() -> {
            
            if (idUsuario == null) {
                throw new IllegalArgumentException("El usuario es obligatorio.");
            }

            if (numeroTarjeta == null || numeroTarjeta.trim().isEmpty()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }

            // Llamamos al repository que ejecuta el nuevo SP
            return cajeroRepository.obtenerDashboardCajero(idUsuario, numeroTarjeta);
        });
    }

    // --- NUEVO MÉTODO PARA RELLENAR CAJEROS ---
    public Result<String> rellenarCajerosSinFondos() {
        // Usamos tu método 'ejecutar()' heredado de BaseService
        return ejecutar(() -> {
            // El repository ejecuta el SP y devuelve un mensaje
            return cajeroRepository.rellenarCajerosSinFondos();
        });
    }

    // --- MÉTODOS ANTERIORES (Por si los usas en otro lado) ---
    public Result<CajeroResponse> listarCajerosPorTarjeta(Integer idUsuario, String numeroTarjeta) {
        return ejecutarLista(() -> {
            if (idUsuario == null) {
                throw new IllegalArgumentException("El usuario es obligatorio.");
            }
            if (numeroTarjeta == null || numeroTarjeta.isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }
            return cajeroRepository.listarCajerosPorTarjeta(idUsuario, numeroTarjeta);
        });
    }

    public Result<InventarioCajeroResponse> consultarInventario(Integer idCajero) {
        return ejecutarLista(() -> {
            if (idCajero == null) {
                throw new IllegalArgumentException("El cajero es obligatorio.");
            }
            return cajeroRepository.consultarInventario(idCajero);
        });
    }
}