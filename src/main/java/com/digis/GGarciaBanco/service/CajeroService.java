package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.cajero.CajeroResponse;
import com.digis.GGarciaBanco.dto.cajero.InventarioCajeroResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.repository.CajeroRetiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CajeroService extends BaseService {

    @Autowired
    private CajeroRetiroRepository cajeroRetiroRepository;

    public Result listarCajerosPorTarjeta(Integer idUsuario, String numeroTarjeta) {
        return ejecutarLista(() -> {

            if (idUsuario == null) {
                throw new IllegalArgumentException("El usuario es obligatorio.");
            }

            // FIX: null debe validarse ANTES de isBlank() para evitar NullPointerException
            if (numeroTarjeta == null || numeroTarjeta.isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }

            StoredProcedureResult<CajeroResponse> result
                    = cajeroRetiroRepository.listarCajerosPorTarjeta(idUsuario, numeroTarjeta);

            if (!Integer.valueOf(0).equals(result.getCodigo())) {
                throw new IllegalArgumentException(result.getMensaje());
            }

            return result.getObjects();
        });
    }

    public Result consultarInventario(Integer idCajero) {
        return ejecutarLista(() -> {

            if (idCajero == null) {
                throw new IllegalArgumentException("El cajero es obligatorio.");
            }

            StoredProcedureResult<InventarioCajeroResponse> spResult
                    = cajeroRetiroRepository.consultarInventario(idCajero);

            if (!Integer.valueOf(0).equals(spResult.getCodigo())) {
                throw new IllegalArgumentException(spResult.getMensaje());
            }

            return spResult.getObjects();
        });
    }

}
