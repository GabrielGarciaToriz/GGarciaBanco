package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.usuario.UsuarioRegistroRequest;
import com.digis.GGarciaBanco.dto.usuario.UsuarioRegistroResponse;
import com.digis.GGarciaBanco.entity.Banco;
import com.digis.GGarciaBanco.entity.Tarjeta;
import com.digis.GGarciaBanco.entity.TokenActivacion;
import com.digis.GGarciaBanco.entity.Usuario;
import com.digis.GGarciaBanco.repository.BancoRepository;
import com.digis.GGarciaBanco.repository.TarjetaRepository;
import com.digis.GGarciaBanco.repository.TokenActivacionRepository;
import com.digis.GGarciaBanco.repository.UsuarioRepository;
import com.digis.GGarciaBanco.util.GeneradorLuhn;
import com.digis.GGarciaBanco.util.GeneradorUUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService extends BaseService {

    private static final int LONGITUD_TARJETA = 16;
    private static final String SECRET_ACTIVACION = "pFhSxlmsSPAYkxuMGkcbrcS4q3B6LkD2t62QR1a2o7y8EmGfKQCxKe4o6t5";
    private static final long EXPIRACION_15_MIN = 15 * 60 * 1000;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TokenActivacionRepository tokenActivacionRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Result<UsuarioRegistroResponse> agregarUsuario(UsuarioRegistroRequest request) {
        return ejecutar(() -> {

            if (request == null) {
                throw new IllegalArgumentException("Los datos del usuario son obligatorios.");
            }

            if (request.getIdBanco() == null) {
                throw new IllegalArgumentException("Debe seleccionar un banco.");
            }

            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new IllegalArgumentException("La contraseña es obligatoria.");
            }

            Banco banco = bancoRepository.findById(request.getIdBanco())
                    .orElseThrow(() -> new NoSuchElementException("El banco seleccionado no existe."));

            if (!Integer.valueOf(1).equals(banco.getActivo())) {
                throw new IllegalArgumentException("El banco seleccionado no está activo.");
            }

            if (banco.getBin() == null) {
                throw new IllegalArgumentException("El banco seleccionado no tiene BIN configurado.");
            }

            Result<?> uuidResult = GeneradorUUID.generarUUID(false);

            if (!uuidResult.isCorrect() || uuidResult.getObject() == null) {
                throw new IllegalStateException("No se pudo generar el UUID del usuario.");
            }

            String uuid = uuidResult.getObject().toString();

            Result<?> tarjetaResult = GeneradorLuhn.generarTarjeta(
                    banco.getBin().toString(),
                    LONGITUD_TARJETA
            );

            if (!tarjetaResult.isCorrect() || tarjetaResult.getObject() == null) {
                throw new IllegalArgumentException(tarjetaResult.getErrorMessage());
            }

            String numeroTarjeta = tarjetaResult.getObject().toString();

            String passwordHash = passwordEncoder.encode(request.getPassword());

            Integer idUsuario = usuarioRepository.agregarUsuario(
                    uuid,
                    request.getNombre(),
                    request.getApellidoPaterno(),
                    request.getApellidoMaterno(),
                    request.getCorreo(),
                    passwordHash,
                    banco.getIdBanco(),
                    numeroTarjeta
            );

            Usuario usuario = usuarioRepository.findByPublicId(uuid)
                    .orElseThrow(() -> new IllegalStateException("Error al recuperar el usuario creado."));

            String tokenActivacion = generarYGuardarToken(usuario);

            try {
                emailService.enviarCorreoVerificacion(
                        request.getCorreo(),
                        request.getNombre(),
                        tokenActivacion
                );
            } catch (Exception e) {
                System.err.println("Error al enviar correo: " + e.getMessage());
            }

            return UsuarioRegistroResponse.builder()
                    .idUsuario(idUsuario)
                    .publicId(uuid)
                    .nombre(request.getNombre())
                    .apellidoPaterno(request.getApellidoPaterno())
                    .apellidoMaterno(request.getApellidoMaterno())
                    .correo(request.getCorreo())
                    .idBanco(banco.getIdBanco())
                    .nombreBanco(banco.getNombreBanco())
                    .bin(banco.getBin().toString())
                    .numeroTarjeta(numeroTarjeta)
                    .build();
        });
    }

    @Transactional
    public Result<Boolean> activarCuenta(String tokenJwt) {
        return ejecutar(() -> {

            if (tokenJwt == null || tokenJwt.isBlank()) {
                throw new IllegalArgumentException("El token de activación es obligatorio.");
            }

            TokenActivacion tokenEntity = tokenActivacionRepository
                    .findByTokenAndUsadoFalse(tokenJwt)
                    .orElseThrow(() -> new IllegalArgumentException(
                    "El enlace de activación ya fue usado o no es válido. Solicita uno nuevo."
            ));

            String publicId;

            try {
                publicId = Jwts.parser()
                        .setSigningKey(SECRET_ACTIVACION.getBytes())
                        .parseClaimsJws(tokenJwt)
                        .getBody()
                        .getSubject();
            } catch (Exception e) {
                throw new IllegalArgumentException(
                        "El enlace de activación ha expirado o no es válido. Solicita uno nuevo."
                );
            }

            Usuario usuario = usuarioRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado."));

            if (Boolean.TRUE.equals(usuario.getActivo())) {
                throw new IllegalArgumentException("Esta cuenta ya fue verificada. Puedes iniciar sesión.");
            }

            tokenEntity.setUsado(true);
            tokenActivacionRepository.save(tokenEntity);

            Integer filasAfectadas = usuarioRepository.activarUsuario(usuario.getIdUsuario());

            if (filasAfectadas == null || filasAfectadas == 0) {
                throw new IllegalStateException("Ocurrió un error al intentar activar la cuenta.");
            }

            Tarjeta tarjeta = tarjetaRepository.findByUsuario_IdUsuario(usuario.getIdUsuario())
                    .orElseThrow(() -> new NoSuchElementException("No se encontró una tarjeta para este usuario."));

            try {
                emailService.enviarCorreoTarjeta(
                        usuario.getCorreo(),
                        usuario.getPublicId(),
                        usuario.getNombre(),
                        tarjeta.getNumeroTarjeta()
                );
            } catch (Exception e) {
                System.err.println("ADVERTENCIA: La cuenta se activó, pero falló el correo final: " + e.getMessage());
            }

            return true;
        });
    }

    @Transactional
    public Result<Boolean> reenviarActivacion(String publicId) {
        return ejecutar(() -> {

            if (publicId == null || publicId.isBlank()) {
                throw new IllegalArgumentException("El publicId del usuario es obligatorio.");
            }

            Usuario usuario = usuarioRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado."));

            if (Boolean.TRUE.equals(usuario.getActivo())) {
                throw new IllegalArgumentException("La cuenta ya está activa.");
            }

            tokenActivacionRepository.invalidarTokensAnteriores(usuario.getIdUsuario());

            String nuevoToken = generarYGuardarToken(usuario);

            emailService.enviarCorreoVerificacion(
                    usuario.getCorreo(),
                    usuario.getNombre(),
                    nuevoToken
            );

            return true;
        });
    }

    public Result<Boolean> verificarEstatus(String publicId) {
        return ejecutar(() -> {

            if (publicId == null || publicId.isBlank()) {
                throw new IllegalArgumentException("El publicId del usuario es obligatorio.");
            }

            Usuario usuario = usuarioRepository.findByPublicId(publicId)
                    .orElseThrow(() -> new NoSuchElementException("Usuario no encontrado."));

            return Boolean.TRUE.equals(usuario.getActivo());
        });
    }

    private String generarYGuardarToken(Usuario usuario) {
        String jwt = Jwts.builder()
                .setSubject(usuario.getPublicId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION_15_MIN))
                .signWith(SignatureAlgorithm.HS256, SECRET_ACTIVACION.getBytes())
                .compact();

        TokenActivacion tokenEntity = TokenActivacion.builder()
                .usuario(usuario)
                .token(jwt)
                .fechaCreacion(LocalDateTime.now())
                .usado(false)
                .build();

        tokenActivacionRepository.save(tokenEntity);

        return jwt;
    }
}
