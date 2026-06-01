package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.cajero.CajeroResponse;
import com.digis.GGarciaBanco.dto.cajero.DashboardCajeroResponse;
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

    // --- NUEVO MÉTODO PARA EL DASHBOARD COMBINADO ---
    @SuppressWarnings("unchecked")
    public DashboardCajeroResponse obtenerDashboardCajero(Integer idUsuario, String numeroTarjeta) {

        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("sp_obtener_dashboard_cajero")
                .declareParameters(
                        // Parámetros de entrada
                        in("p_id_usuario", Types.NUMERIC),
                        in("p_numero_tarjeta", Types.VARCHAR),
                        // Parámetros de salida (Datos del cajero)
                        out("p_id_cajero", Types.NUMERIC),
                        out("p_nombre_cajero", Types.VARCHAR),
                        out("p_cajero_activo", Types.NUMERIC),
                        out("p_id_banco", Types.NUMERIC),
                        out("p_nombre_banco", Types.VARCHAR),
                        // Cursor (Inventario)
                        outCursor("p_inventario"),
                        // Control
                        out("p_codigo", Types.NUMERIC),
                        out("p_mensaje", Types.VARCHAR)
                )
                .returningResultSet("p_inventario", (rs, n) -> {
                    InventarioCajeroResponse i = new InventarioCajeroResponse();
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
        params.put("p_id_usuario", idUsuario);
        params.put("p_numero_tarjeta", numeroTarjeta);

        Map<String, Object> result = call.execute(params);

        // Manejo de error desde la BD
        if (codigo(result) != 0) {
            throw new RuntimeException(mensaje(result));
        }

        // 1. Armamos el objeto del Cajero desde los parámetros OUT
        CajeroResponse cajero = new CajeroResponse();
        Number idCajero = (Number) result.get("p_id_cajero");
        cajero.setIdCajero(idCajero != null ? idCajero.intValue() : 0);
        cajero.setNombreCajero((String) result.get("p_nombre_cajero"));

        Number activo = (Number) result.get("p_cajero_activo");
        cajero.setActivo(activo != null && activo.intValue() == 1);

        Number idBanco = (Number) result.get("p_id_banco");
        cajero.setIdBanco(idBanco != null ? idBanco.intValue() : 0);
        cajero.setNombreBanco((String) result.get("p_nombre_banco"));

        // 2. Extraemos la lista del Inventario
        List<InventarioCajeroResponse> inventario = (List<InventarioCajeroResponse>) result.get("p_inventario");

        // (Opcional) Asignamos el ID del cajero a cada item del inventario para mantener consistencia
        if (inventario != null && cajero.getIdCajero() != null) {
            for (InventarioCajeroResponse item : inventario) {
                item.setIdCajero(cajero.getIdCajero());
            }
        }

        // 3. Devolvemos el DTO combinado
        return new DashboardCajeroResponse(cajero, inventario);
    }

    // --- NUEVO MÉTODO PARA RELLENAR CAJEROS ---
    public String rellenarCajerosSinFondos() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbc)
                .withProcedureName("sp_rellenar_cajeros_sin_fondos")
                .declareParameters(
                        out("p_cajeros_rellenados", Types.NUMERIC),
                        out("p_codigo", Types.NUMERIC),
                        out("p_mensaje", Types.VARCHAR)
                );

        // No hay parámetros de entrada, ejecutamos directo
        Map<String, Object> result = call.execute();

        if (codigo(result) != 0) {
            throw new RuntimeException(mensaje(result));
        }

        // Retornamos el mensaje de éxito de la BD
        return mensaje(result);
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
