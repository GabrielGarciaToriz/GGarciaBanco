package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.cajero.CajeroResponse;
import com.digis.GGarciaBanco.dto.cajero.InventarioCajeroResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.repository.CajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService extends BaseService {

    @Autowired
    private CajeroRepository cajeroRepository;

    public Result<CajeroResponse> listarCajerosPorTarjeta(Integer idUsuario, String numeroTarjeta) {
        return ejecutarLista(() -> {

            if (idUsuario == null) {
                throw new IllegalArgumentException("El usuario es obligatorio.");
            }

            if (numeroTarjeta == null || numeroTarjeta.isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }

            // El repository ya lanza RuntimeException si codigo != 0
            return cajeroRepository.listarCajerosPorTarjeta(idUsuario, numeroTarjeta);
        });
    }

    public Result<InventarioCajeroResponse> consultarInventario(Integer idCajero) {
        return ejecutarLista(() -> {

            if (idCajero == null) {
                throw new IllegalArgumentException("El cajero es obligatorio.");
            }

            // El repository ya lanza RuntimeException si codigo != 0
            return cajeroRepository.consultarInventario(idCajero);
        });
    }
}
