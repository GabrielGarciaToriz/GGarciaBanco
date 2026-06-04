package com.digis.ggarciabanco.service;

import com.digis.ggarciabanco.dto.Result;
import com.digis.ggarciabanco.dto.retiro.MovimientoResponse;
import com.digis.ggarciabanco.dto.retiro.RetiroRequest;
import com.digis.ggarciabanco.dto.retiro.RetiroResponse;
import com.digis.ggarciabanco.dto.sp.StoredProcedureResult;
import com.digis.ggarciabanco.repository.CajeroRepository;
import com.digis.ggarciabanco.repository.RetiroRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetiroService extends BaseService {

    @Autowired
    private RetiroRepository retiroRepository;

    @Transactional
    public Result<RetiroResponse> retirar(RetiroRequest request) {
        return ejecutar(() -> {

            if (request == null) {
                throw new IllegalArgumentException("Los datos del retiro son obligatorios.");
            }

            if (request.getIdUsuario() == null) {
                throw new IllegalArgumentException("El usuario es obligatorio.");
            }

            if (request.getNumeroTarjeta() == null || request.getNumeroTarjeta().isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }

            if (request.getIdCajero() == null) {
                throw new IllegalArgumentException("El cajero es obligatorio.");
            }

            if (request.getMonto() == null) {
                throw new IllegalArgumentException("El monto es obligatorio.");
            }

            if (request.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("El monto debe ser mayor a cero.");
            }

            return retiroRepository.retirar(
                    request.getIdUsuario(),
                    request.getNumeroTarjeta(),
                    request.getIdCajero(),
                    request.getMonto()
            );
        });
    }

    public Result<List<MovimientoResponse>> obtenerMovimientos(String publicId) {
        return ejecutar(() -> {

            if (publicId.isBlank() || publicId.isEmpty()) {
                throw new IllegalArgumentException("El ID del usuario es inválido.");
            }

            List<MovimientoResponse> movimientos = retiroRepository.obtenerMovimientos(publicId);

            if (movimientos == null || movimientos.isEmpty()) {
                throw new NoSuchElementException("No se encontraron movimientos para este usuario.");
            }

            return movimientos;
        });
    }
}
