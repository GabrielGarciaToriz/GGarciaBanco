package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.usuario.UsuarioRegistroRequest;
import com.digis.GGarciaBanco.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController extends BaseController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Result<?>> agregarUsuario(@RequestBody UsuarioRegistroRequest usuario) {
        return responderCreado(usuarioService.agregarUsuario(usuario));
    }

    @PostMapping("/activar")
    public ResponseEntity<Result<?>> activarCuenta(@RequestParam String token) {
        return responder(usuarioService.activarCuenta(token));
    }

    @PostMapping("/reenviar-activacion/{publicId}")
    public ResponseEntity<Result<?>> reenviarActivacion(@PathVariable String publicId) {
        return responder(usuarioService.reenviarActivacion(publicId));
    }

    @GetMapping("/estatus/{publicId}")
    public ResponseEntity<Result<?>> verificarEstatus(@PathVariable String publicId) {
        return responder(usuarioService.verificarEstatus(publicId));
    }
}
