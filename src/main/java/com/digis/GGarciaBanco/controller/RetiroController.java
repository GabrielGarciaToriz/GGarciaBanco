package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.retiro.HistorialMovimientoResponse;
import com.digis.GGarciaBanco.dto.retiro.RetiroRequest;
import com.digis.GGarciaBanco.service.HistorialMovimientoService;
import com.digis.GGarciaBanco.service.RetiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/retiro")
public class RetiroController extends BaseController {

    @Autowired
    private RetiroService retiroService;
    @Autowired
    private HistorialMovimientoService HistorialMovimientoService;

    @PostMapping
    public ResponseEntity<Result<?>> retirar(
            @RequestBody RetiroRequest request
    ) {
        return responder(retiroService.retirar(request));
    }

    @GetMapping("/movimientos/{publicId}")
    public ResponseEntity<Result<?>> obtenerMovimientosUsuario(@PathVariable String publicId) {
        return responder(retiroService.obtenerMovimientos(publicId));
    }

    @GetMapping("/historial/{publicId}")
    public ResponseEntity<Result<HistorialMovimientoResponse>> obtenerHistorialPorPublicId(
            @PathVariable String publicId
    ) {
        Result<HistorialMovimientoResponse> result
                = HistorialMovimientoService.obtenerHistorialPorPublicId(publicId);

        if (result.isCorrect()) {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
