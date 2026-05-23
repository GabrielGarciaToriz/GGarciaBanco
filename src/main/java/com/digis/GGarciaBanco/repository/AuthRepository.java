package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.auth.LoginRequest;
import com.digis.GGarciaBanco.dto.auth.LoginResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.util.SpHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional()
    public StoredProcedureResult<LoginResponse> login(LoginRequest request) {
        StoredProcedureQuery sp = entityManager
                .createStoredProcedureQuery("sp_login_tarjeta");

        sp.registerStoredProcedureParameter("p_numero_tarjeta", String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN);

        sp.registerStoredProcedureParameter("p_id_usuario", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_id_tarjeta", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_nombre_usuario", String.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_id_banco", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_nombre_banco", String.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_codigo", Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_mensaje", String.class, ParameterMode.OUT);

        sp.setParameter("p_numero_tarjeta", request.getNumeroTarjeta());
        sp.setParameter("p_password", request.getPassword());

        sp.execute();

        Integer codigo = SpHelper.toInteger(sp.getOutputParameterValue("p_codigo"));
        String mensaje = SpHelper.toStringValue(sp.getOutputParameterValue("p_mensaje"));

        LoginResponse response = null;

        if (Integer.valueOf(0).equals(codigo)) {
            response = LoginResponse.builder()
                    .idUsuario(SpHelper.toInteger(sp.getOutputParameterValue("p_id_usuario")))
                    .idTarjeta(SpHelper.toInteger(sp.getOutputParameterValue("p_id_tarjeta")))
                    .numeroTarjeta(request.getNumeroTarjeta())
                    .nombreUsuario(SpHelper.toStringValue(sp.getOutputParameterValue("p_nombre_usuario")))
                    .idBanco(SpHelper.toInteger(sp.getOutputParameterValue("p_id_banco")))
                    .nombreBanco(SpHelper.toStringValue(sp.getOutputParameterValue("p_nombre_banco")))
                    .build();
        }

        return StoredProcedureResult.<LoginResponse>builder()
                .codigo(codigo)
                .mensaje(mensaje)
                .object(response)
                .build();
    }

}
