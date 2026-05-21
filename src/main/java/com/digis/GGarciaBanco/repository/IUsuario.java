package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.entity.Usuario;

public interface IUsuario {
    public Result agregarUsuario(Usuario usuario);
}
