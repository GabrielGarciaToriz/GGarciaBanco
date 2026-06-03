package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.repository.TarjetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImp implements UserDetailsService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Override
    public UserDetails loadUserByUsername(String numeroTarjeta)
            throws UsernameNotFoundException {
        return tarjetaRepository
                .findByNumeroTarjeta(numeroTarjeta)
                .map(tarjeta -> tarjeta.getUsuario())
                .orElseThrow(() -> new UsernameNotFoundException(
                "Tarjeta no encontrada: " + numeroTarjeta));
    }
}
