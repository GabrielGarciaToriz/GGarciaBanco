package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.password.CambiarPasswordRequest;
import com.digis.GGarciaBanco.dto.password.SolicitarResetRequest;
import com.digis.GGarciaBanco.service.PasswordResetService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/password")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    /**
     * POST /api/auth/password/solicitar Recibe el correo y envia el link de
     * reset al usuario. La respuesta es generica por seguridad (no revela si el
     * correo existe).
     */
    @PostMapping("/solicitar")
    public ResponseEntity<Map<String, String>> solicitar(
            @Valid @RequestBody SolicitarResetRequest req) {
        try {
            passwordResetService.solicitarReset(req.correo());
        } catch (Exception ignored) {
            // Ignorado intencionalmente: no se filtra si el correo existe
        }
        return ResponseEntity.ok(Map.of(
                "mensaje", "Si el correo esta registrado, recibiras un enlace en breve."
        ));
    }

    /**
     * GET /api/auth/password/validar?token=xxx Verifica si el token es valido y
     * no ha expirado.
     */
    @GetMapping("/validar")
    public ResponseEntity<Map<String, Boolean>> validar(
            @RequestParam String token) {
        return ResponseEntity.ok(
                Map.of("valido", passwordResetService.validarToken(token))
        );
    }

    /**
     * POST /api/auth/password/cambiar Recibe el token y la nueva contrasena,
     * actualiza en BD.
     */
    @PostMapping("/cambiar")
    public ResponseEntity<Map<String, String>> cambiar(
            @Valid @RequestBody CambiarPasswordRequest req) {
        passwordResetService.cambiarPassword(req.token(), req.nuevaPassword());
        return ResponseEntity.ok(Map.of(
                "mensaje", "Contrasena actualizada correctamente."
        ));
    }
}
