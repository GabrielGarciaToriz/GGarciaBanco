package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.repository.TokenResetPasswordRepository;
import com.digis.GGarciaBanco.repository.UsuarioRepository;
import com.digis.GGarciaBanco.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.security.SecureRandom;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final TokenResetPasswordRepository tokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    private static final int EXPIRACION_MINUTOS = 15;

    // ----------------------------------------------------------------
    // Solicitar reset: genera token y llama a SP_SOLICITAR_RESET
    // ----------------------------------------------------------------
    @Transactional
    public void solicitarReset(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        String tokenRaw = generarToken();

        StoredProcedureQuery sp = em
                .createStoredProcedureQuery("SP_SOLICITAR_RESET")
                .registerStoredProcedureParameter("P_ID_USUARIO",  Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_TOKEN",       String.class,  ParameterMode.IN)
                .registerStoredProcedureParameter("P_MINUTOS_EXP", Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter("P_CODIGO",      Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("P_MENSAJE",     String.class,  ParameterMode.OUT)
                .setParameter("P_ID_USUARIO",  usuario.getIdUsuario())
                .setParameter("P_TOKEN",       tokenRaw)
                .setParameter("P_MINUTOS_EXP", EXPIRACION_MINUTOS);

        sp.execute();

        Integer codigo  = (Integer) sp.getOutputParameterValue("P_CODIGO");
        String  mensaje = (String)  sp.getOutputParameterValue("P_MENSAJE");

        if (codigo != 0) {
            throw new RuntimeException("Error al guardar token: " + mensaje);
        }

        emailService.enviarCorreoResetPassword(
                correo,
                usuario.getNombre(),
                tokenRaw
        );
    }

    // ----------------------------------------------------------------
    // Cambiar password: valida token y llama a SP_CAMBIAR_PASSWORD_RESET
    // ----------------------------------------------------------------
    @Transactional
    public void cambiarPassword(String token, String nuevaPassword) {
        String passwordHash = passwordEncoder.encode(nuevaPassword);

        StoredProcedureQuery sp = em
                .createStoredProcedureQuery("SP_CAMBIAR_PASSWORD_RESET")
                .registerStoredProcedureParameter("P_TOKEN",         String.class,  ParameterMode.IN)
                .registerStoredProcedureParameter("P_PASSWORD_HASH", String.class,  ParameterMode.IN)
                .registerStoredProcedureParameter("P_CODIGO",        Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter("P_MENSAJE",       String.class,  ParameterMode.OUT)
                .setParameter("P_TOKEN",         token)
                .setParameter("P_PASSWORD_HASH", passwordHash);

        sp.execute();

        Integer codigo  = (Integer) sp.getOutputParameterValue("P_CODIGO");
        String  mensaje = (String)  sp.getOutputParameterValue("P_MENSAJE");

        if (codigo != 0) {
            throw new RuntimeException(mensaje);
        }
    }

    // ----------------------------------------------------------------
    // Validar token: consulta directa via repository (GET /validar)
    // ----------------------------------------------------------------
    public boolean validarToken(String token) {
        return tokenRepository.existeTokenValido(token);
    }

    // ----------------------------------------------------------------
    // Genera token seguro: 48 bytes SecureRandom -> Base64 URL-safe
    // ----------------------------------------------------------------
    private String generarToken() {
        byte[] bytes = new byte[48];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}