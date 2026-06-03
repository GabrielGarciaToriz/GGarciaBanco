package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.catalogo.BancoResponse;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CatalogoRepository extends BaseRepository {

    public CatalogoRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    public List<BancoResponse> obtenerBancos() {
        return jdbc.query(
                "SELECT id_banco, nombre_banco, activo FROM banco WHERE activo = 1 ORDER BY nombre_banco",
                (rs, n) -> {
                    BancoResponse b = new BancoResponse();
                    b.setIdBanco(rs.getInt("id_banco"));
                    b.setNombreBanco(rs.getString("nombre_banco"));
                    b.setActivo(rs.getInt("activo"));
                    return b;
                }
        );
    }

}
