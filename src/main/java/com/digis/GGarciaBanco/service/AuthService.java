package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.auth.AuthResponse;
import com.digis.GGarciaBanco.dto.login.LoginRequest;
import com.digis.GGarciaBanco.dto.login.LoginResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.entity.Tarjeta;
import com.digis.GGarciaBanco.entity.Usuario;
import com.digis.GGarciaBanco.repository.AuthRepository;
import com.digis.GGarciaBanco.repository.TarjetaRepository;
import com.digis.GGarciaBanco.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TarjetaRepository tarjetaRepository;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNumeroTarjeta(),
                        request.getPassword()
                )
        );

        Tarjeta tarjeta = tarjetaRepository
                .findByNumeroTarjetaAndActivaTrue(request.getNumeroTarjeta())
                .orElseThrow(() -> new UsernameNotFoundException("Tarjeta no encontrada"));

        Usuario usuario = tarjeta.getUsuario();
        String token = jwtUtil.generateToken(usuario);

        return new AuthResponse(
                token,
                usuario.getNombre() + " " + usuario.getApellidoPaterno(),
                tarjeta.getNumeroTarjeta(),
                usuario.getIdUsuario().longValue(),
                tarjeta.getBanco().getNombreBanco()
        );
    }

}
