package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digis.GGarciaBanco.repository.BancoRepository;

@Service
public class CatalogoService extends BaseService {

    @Autowired
    private BancoRepository bancoRepository;

    public Result obtenerBancos() {
        return ejecutarLista(() -> bancoRepository.findAll());
    }

}
