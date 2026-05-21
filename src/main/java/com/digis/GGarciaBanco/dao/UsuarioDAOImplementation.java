package com.digis.GGarciaBanco.dao;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.entity.Tarjeta;
import com.digis.GGarciaBanco.entity.Usuario;
import com.digis.GGarciaBanco.repository.IUsuario;
import com.digis.GGarciaBanco.util.GeneradorUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuario {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result agregarUsuario(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL AGREGAR_USUARIO(?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                Result unico = GeneradorUUID.generarUUID(false);
                callableStatement.setString(1, unico.object.toString());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getCorreo());
                callableStatement.setString(6, usuario.getPassword());
                Tarjeta tarjeta = usuario.getTarjeta().get(0);
                callableStatement.setInt(7, tarjeta.getBanco().getIdBanco());
                callableStatement.setString(8, tarjeta.getNumeroTarjeta());
                callableStatement.registerOutParameter(9, java.sql.Types.INTEGER);
                boolean resultado = callableStatement.execute();
                if (!resultado) {
                    return result.correct = true;
                }
                return result.correct = false;
            });

        } catch (Exception e) {
            result.correct = false;
            result.errorMessage = e.getLocalizedMessage();
            result.ex = e;
        }
        return result;
    }

}
