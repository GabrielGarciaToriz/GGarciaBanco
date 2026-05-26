package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.cajero.CajeroConsultaRequest;
import com.digis.GGarciaBanco.service.CajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/cajero")
public class CajeroController extends BaseController {

    @Autowired
    private CajeroService cajeroService;

    @PostMapping("/tarjeta")
    public ResponseEntity<Result<?>> listarCajeroporTarjeta(
            @RequestBody CajeroConsultaRequest request
    ) {
        return responder(cajeroService.listarCajerosPorTarjeta(
                request.getIdUsuario(),
                request.getNumeroTarjeta()
        ));
    }
 
    @GetMapping("/{idCajero}/inventario")
    public ResponseEntity<Result<?>> consultarInventario(@PathVariable Integer idCajero) {
        return responder(cajeroService.consultarInventario(idCajero));
    }

}
