package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.usuario.UsuarioRegistroRequest;
import com.digis.GGarciaBanco.dto.usuario.UsuarioRegistroResponse;
import com.digis.GGarciaBanco.entity.Banco;
import com.digis.GGarciaBanco.repository.BancoRepository;
import com.digis.GGarciaBanco.repository.UsuarioRepository;
import com.digis.GGarciaBanco.util.GeneradorLuhn;
import com.digis.GGarciaBanco.util.GeneradorUUID;
import jakarta.transaction.Transactional;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsuarioService extends BaseService {

    private static final int LONGITUD_TARJETA = 16;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BancoRepository bancoRepository;

    // FIX: encoder declarado como campo para no instanciarlo en cada llamada
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Result agregarUsuario(UsuarioRegistroRequest request) {
        return ejecutar(() -> {

            if (request == null) {
                throw new IllegalArgumentException("Los datos del usuario son obligatorios.");
            }

            if (request.getIdBanco() == null) {
                throw new IllegalArgumentException("Debe seleccionar un banco.");
            }

            Banco banco = bancoRepository.findById(request.getIdBanco())
                    .orElseThrow(() -> new NoSuchElementException("El banco seleccionado no existe."));

            if (!Integer.valueOf(1).equals(banco.getActivo())) {
                throw new IllegalArgumentException("El banco seleccionado no está activo.");
            }

            if (banco.getBin() == null) {
                throw new IllegalArgumentException("El banco seleccionado no tiene BIN configurado.");
            }

            Result uuidResult = GeneradorUUID.generarUUID(false);
            if (!uuidResult.correct || uuidResult.object == null) {
                throw new IllegalStateException("No se pudo generar el UUID del usuario.");
            }
            String uuid = uuidResult.object.toString();

            Result tarjetaResult = GeneradorLuhn.generarTarjeta(
                    banco.getBin().toString(),
                    LONGITUD_TARJETA
            );
            if (!tarjetaResult.correct || tarjetaResult.object == null) {
                throw new IllegalArgumentException(tarjetaResult.errorMessage);
            }
            String numeroTarjeta = tarjetaResult.object.toString();

            // FIX: password hasheado con BCrypt antes de enviarlo al stored procedure.
            // Si el SP ya hashea internamente, elimina esta línea y el campo passwordEncoder.
            String passwordHash = passwordEncoder.encode(request.getPassword());

            Integer idUsuario = usuarioRepository.agregarUsuario(
                    uuid,
                    request.getNombre(),
                    request.getApellidoPaterno(),
                    request.getApellidoMaterno(),
                    request.getCorreo(),
                    passwordHash, // FIX: se envía el hash, no el texto plano
                    banco.getIdBanco(),
                    numeroTarjeta
            );

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

}
