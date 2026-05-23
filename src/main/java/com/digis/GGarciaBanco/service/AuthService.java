package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.dto.auth.LoginRequest;
import com.digis.GGarciaBanco.dto.auth.LoginResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends BaseService {

    @Autowired
    private AuthRepository authRepository;

    public Result<LoginResponse> login(LoginRequest request) {

        if (request == null) {
            return Result.error("INVALID_INPUT", "Los datos de inicio de sesión son obligatorios.");
        }

        return ejecutar(() -> {

            if (request.getNumeroTarjeta() == null || request.getNumeroTarjeta().isBlank()) {
                throw new IllegalArgumentException("El número de tarjeta es obligatorio.");
            }

            if (request.getPassword() == null || request.getPassword().isBlank()) {
                throw new IllegalArgumentException("La contraseña es obligatoria.");
            }

            StoredProcedureResult<LoginResponse> spResult = authRepository.login(request);

            if (spResult.getCodigo() == null) {
                throw new IllegalStateException("El procedimiento almacenado no devolvió código de respuesta.");
            }

            if (!Integer.valueOf(0).equals(spResult.getCodigo())) {
                throw new IllegalArgumentException(spResult.getMensaje());
            }

            if (spResult.getObject() == null) {
                throw new IllegalStateException("El procedimiento almacenado respondió exitosamente pero no devolvió datos de sesión.");
            }

            return spResult.getObject();
        });
    }

}
