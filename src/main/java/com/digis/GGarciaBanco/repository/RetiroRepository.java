package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.retiro.RetiroResponse;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class RetiroRepository extends BaseRepository {

    public RetiroRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

    @SuppressWarnings("unchecked")
    public RetiroResponse retirar(Integer idUsuario, String numeroTarjeta,
            Integer idCajero, BigDecimal monto) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("sp_retiro_efectivo")
                .declareParameters(
                        in("p_id_usuario", Types.NUMERIC),
                        in("p_numero_tarjeta", Types.VARCHAR),
                        in("p_id_cajero", Types.NUMERIC),
                        in("p_monto", Types.NUMERIC),
                        out("p_id_retiro", Types.NUMERIC),
                        out("p_codigo", Types.NUMERIC),
                        out("p_mensaje", Types.VARCHAR),
                        outCursor("p_detalle")
                )
                .returningResultSet("p_detalle", (rs, n) -> {
                    RetiroResponse.DetalleRetiroResponse d = new RetiroResponse.DetalleRetiroResponse();
                    d.setIdDenominacion(rs.getInt("id_denominacion"));
                    d.setTipo(rs.getString("tipo"));
                    d.setValorCentavos(rs.getBigDecimal("valor_centavos"));
                    d.setValor(rs.getBigDecimal("valor"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setSubtotalCentavos(rs.getBigDecimal("subtotal_centavos"));
                    d.setSubtotal(rs.getBigDecimal("subtotal"));
                    return d;
                });

        Map<String, Object> params = new HashMap<>();
        params.put("p_id_usuario", idUsuario);
        params.put("p_numero_tarjeta", numeroTarjeta);
        params.put("p_id_cajero", idCajero);
        params.put("p_monto", monto);

        Map<String, Object> result = call.execute(params);
        if (codigo(result) != 0) {
            throw new RuntimeException(mensaje(result));
        }

        RetiroResponse resp = new RetiroResponse();
        resp.setIdRetiro(result.get("p_id_retiro") != null ? ((Number) result.get("p_id_retiro")).intValue() : null);
        resp.setMontoRetiro(monto);
        resp.setDetalle((List<RetiroResponse.DetalleRetiroResponse>) result.get("p_detalle"));
        return resp;
    }

}
