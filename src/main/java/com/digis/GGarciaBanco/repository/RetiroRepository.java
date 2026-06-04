package com.digis.ggarciabanco.repository;

import com.digis.ggarciabanco.dto.retiro.MovimientoResponse;
import com.digis.ggarciabanco.dto.retiro.RetiroResponse;
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
    public RetiroResponse retirar(
            Integer idUsuario,
            String numeroTarjeta,
            Integer idCajero,
            BigDecimal monto) {

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

    @SuppressWarnings("unchecked")
    public List<MovimientoResponse> obtenerMovimientos(String publicId) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("obtener_movimientos_usuario")
                .declareParameters(
                        in("p_public_id", Types.VARCHAR),
                        outCursor("p_cursor")
                )
                .returningResultSet("p_cursor", (rs, n) -> {
                    MovimientoResponse m = new MovimientoResponse();
                    m.setIdRetiro(rs.getInt("id_retiro"));
                    m.setMonto(rs.getBigDecimal("monto_centavos")); // Coincide con tu BD
                    m.setNumeroTarjeta(rs.getString("numero_tarjeta"));
                    m.setNombreCajero(rs.getString("nombre"));      // Coincide con tu BD
                    m.setFecha(rs.getDate("fecha"));                // Coincide con tu BD
                    return m;
                });

        Map<String, Object> params = new HashMap<>();
        params.put("p_public_id", publicId);

        Map<String, Object> result = call.execute(params);
        return (List<MovimientoResponse>) result.get("p_cursor");
    }

}
