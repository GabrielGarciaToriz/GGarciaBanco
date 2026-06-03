package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.cajero.CajeroConsultaRequest;
import com.digis.GGarciaBanco.service.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cajero")
public class CajeroController extends BaseController {

    @Autowired
    private CajeroService cajeroService;

    @PostMapping("/dashboard")
    public ResponseEntity<Result<?>> obtenerDashboard(
            @RequestBody CajeroConsultaRequest request
    ) {
        return responder(cajeroService.obtenerDashboardCajero(
                request.getIdUsuario(),
                request.getNumeroTarjeta()
        ));
    }

    @PostMapping("/rellenar")
    public ResponseEntity<Result<?>> rellenarCajerosVacios() {
        return responder(cajeroService.rellenarCajerosSinFondos());
    }


}
