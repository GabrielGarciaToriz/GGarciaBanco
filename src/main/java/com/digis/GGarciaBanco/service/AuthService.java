package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.auth.AuthResponse;
import com.digis.GGarciaBanco.dto.login.LoginRequest;
import com.digis.GGarciaBanco.entity.Tarjeta;
import com.digis.GGarciaBanco.entity.Usuario;
import com.digis.GGarciaBanco.exception.ErrorCode;
import com.digis.GGarciaBanco.repository.TarjetaRepository;
import com.digis.GGarciaBanco.util.JwtUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService extends BaseService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TarjetaRepository tarjetaRepository;

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    public Result<Object> login(LoginRequest request) {

        if (request == null
                || request.getNumeroTarjeta() == null
                || request.getNumeroTarjeta().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {
            return ejecutar(() -> {
                throw new IllegalArgumentException("Los datos de inicio de sesión son obligatorios.");
            });
        }

        Optional<Tarjeta> tarjetaOpt = tarjetaRepository.findByNumeroTarjeta(request.getNumeroTarjeta());

        if (tarjetaOpt.isEmpty()) {
            return ejecutar(() -> {
                throw new IllegalArgumentException("Número de tarjeta o contraseña incorrectos.");
            });
        }

        Tarjeta tarjeta = tarjetaOpt.get();
        Usuario usuario = tarjeta.getUsuario();

        // Corrección: Boolean.TRUE.equals() — funciona correctamente con Boolean wrapper
        // Cubre los casos: null, false, y true de forma segura
        boolean cuentaInactiva = !Boolean.TRUE.equals(usuario.getActivo())
                              || !Boolean.TRUE.equals(tarjeta.getActiva());

        if (cuentaInactiva) {

            try {
                Result<Boolean> reenvio = usuarioService.reenviarActivacion(usuario.getPublicId());
                if (!reenvio.correct) {
                    System.err.println("reenviarActivacion falló: " + reenvio.errorMessage);
                }
            } catch (Exception e) {
                System.err.println("Excepción en reenviarActivacion: " + e.getMessage());
            }

            Result<Object> resultado = new Result<>();
            resultado.setCorrect(false);
            resultado.setErrorCode(ErrorCode.ACCOUNT_NOT_ACTIVATED);
            resultado.setErrorMessage("CUENTA_INACTIVA");
            resultado.setObject(usuario.getPublicId());
            return resultado;
        }

        return ejecutar(() -> {
            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getNumeroTarjeta(),
                                request.getPassword()
                        )
                );
            } catch (AuthenticationException e) {
                throw new IllegalArgumentException("Número de tarjeta o contraseña incorrectos.");
            }

            String token = jwtUtil.generateToken(usuario);

            return (Object) new AuthResponse(
                    token,
                    usuario.getNombre() + " " + usuario.getApellidoPaterno(),
                    tarjeta.getNumeroTarjeta(),
                    usuario.getIdUsuario().longValue(),
                    tarjeta.getBanco().getNombreBanco(),
                    usuario.getPublicId()
            );
        });
    }
}