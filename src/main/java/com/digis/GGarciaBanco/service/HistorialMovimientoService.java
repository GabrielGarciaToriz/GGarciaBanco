package com.digis.ggarciabanco.service;

import com.digis.ggarciabanco.dto.Result;
import com.digis.ggarciabanco.dto.retiro.HistorialMovimientoResponse;
import com.digis.ggarciabanco.repository.HistorialMovimientoResponseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HistorialMovimientoService {

    @Autowired
    private HistorialMovimientoResponseRepository movimientoResponseRepository;

    public Result<HistorialMovimientoResponse> obtenerHistorialPorPublicId(String publicId) {
        try {
            if (publicId == null || publicId.isBlank()) {
                return Result.error("PUBLIC_ID_REQUIRED", "EL public_id es necesario");
            }
            List<HistorialMovimientoResponse> historial = movimientoResponseRepository.obtenerHistorialPorPublicId(publicId);
            return Result.okList(historial);
        } catch (Exception e) {
            return Result.error("HISTORIAL_MOVIMIENTOS_ERROR", "OCURRIO UN ERROR", e);
        }
    }
}
