package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.cajero.CajeroResponse;
import com.digis.GGarciaBanco.dto.cajero.InventarioCajeroResponse;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import org.springframework.stereotype.Repository;

@Repository
public class CajeroRepository extends BaseRepository {

    public CajeroRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @SuppressWarnings("unchecked")
    public List<CajeroResponse> listarCajerosPorTarjeta(Integer idUsuario, String numeroTarjeta) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("sp_listar_cajeros_por_tarjeta")
                .declareParameters(
                        in("p_id_usuario", Types.NUMERIC),
                        in("p_numero_tarjeta", Types.VARCHAR),
                        outCursor("p_cajeros"),
                        out("p_codigo", Types.NUMERIC),
                        out("p_mensaje", Types.VARCHAR)
                )
                .returningResultSet("p_cajeros", (rs, n) -> {
                    CajeroResponse c = new CajeroResponse();
                    c.setIdCajero(rs.getInt("id_cajero"));
                    c.setNombreCajero(rs.getString("nombre_cajero"));
                    c.setActivo(rs.getBoolean("activo"));
                    c.setIdBanco(rs.getInt("id_banco"));
                    c.setNombreBanco(rs.getString("nombre_banco"));
                    return c;
                });

        Map<String, Object> params = new HashMap<>();
        params.put("p_id_usuario", idUsuario);
        params.put("p_numero_tarjeta", numeroTarjeta);

        Map<String, Object> result = call.execute(params);
        if (codigo(result) != 0) {
            throw new RuntimeException(mensaje(result));
        }
        return (List<CajeroResponse>) result.get("p_cajeros");
    }

    @SuppressWarnings("unchecked")
    public List<InventarioCajeroResponse> consultarInventario(Integer idCajero) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("sp_consultar_inventario_cajero")
                .declareParameters(
                        in("p_id_cajero", Types.NUMERIC),
                        outCursor("p_inventario"),
                        out("p_codigo", Types.NUMERIC),
                        out("p_mensaje", Types.VARCHAR)
                )
                .returningResultSet("p_inventario", (rs, n) -> {
                    InventarioCajeroResponse i = new InventarioCajeroResponse();
                    i.setIdCajero(rs.getInt("id_cajero"));
                    i.setIdDenominacion(rs.getInt("id_denominacion"));
                    i.setTipo(rs.getString("tipo"));
                    i.setValorCentavos(rs.getBigDecimal("valor_centavos"));
                    i.setValor(rs.getBigDecimal("valor"));
                    i.setCantidadActual(rs.getInt("cantidad_actual"));
                    i.setTotalCentavos(rs.getBigDecimal("total_centavos"));
                    i.setTotal(rs.getBigDecimal("total"));
                    return i;
                });

        Map<String, Object> params = new HashMap<>();
        params.put("p_id_cajero", idCajero);

        Map<String, Object> result = call.execute(params);
        if (codigo(result) != 0) {
            throw new RuntimeException(mensaje(result));
        }
        return (List<InventarioCajeroResponse>) result.get("p_inventario");
    }

}
