package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dao.UsuarioDAOImplementation;
import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController extends BaseController {

    @Autowired
    public UsuarioDAOImplementation usuarioDAO;

    @PostMapping()
    public ResponseEntity<Result> agregarUsuario(@RequestBody Usuario usuario) {
        return responderCreado(usuarioDAO.agregarUsuario(usuario));
    }

}
