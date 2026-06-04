package com.digis.ggarciabanco.service;

import com.digis.ggarciabanco.dto.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.digis.ggarciabanco.repository.BancoRepository;

@Service
public class CatalogoService extends BaseService {

    @Autowired
    private BancoRepository bancoRepository;

    public Result obtenerBancos() {
        return ejecutarLista(() -> bancoRepository.findAll());
    }

}
