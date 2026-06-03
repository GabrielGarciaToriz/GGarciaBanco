package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/catalogo")
public class CatalogoController extends BaseController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping("/banco")
    public ResponseEntity<Result<?>> obtenerBancos() {
        return responder(catalogoService.obtenerBancos());
    }
}