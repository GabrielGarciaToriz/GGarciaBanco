package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.dto.cajero.CajeroResponse;
import com.digis.GGarciaBanco.dto.cajero.InventarioCajeroResponse;
import com.digis.GGarciaBanco.dto.retiro.RetiroDetalleResponse;
import com.digis.GGarciaBanco.dto.retiro.RetiroRequest;
import com.digis.GGarciaBanco.dto.retiro.RetiroResponse;
import com.digis.GGarciaBanco.dto.sp.StoredProcedureResult;
import com.digis.GGarciaBanco.util.SpHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CajeroRetiroRepository {
    @PersistenceContext
    private EntityManager entityManager;
 
    @Transactional()
    public StoredProcedureResult<CajeroResponse> listarCajerosPorTarjeta(
            Integer idUsuario,
            String numeroTarjeta
    ) {
        StoredProcedureQuery sp = entityManager
                .createStoredProcedureQuery("sp_listar_cajeros_por_tarjeta");
 
        sp.registerStoredProcedureParameter("p_id_usuario",     Integer.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_numero_tarjeta",  String.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_cajeros",          void.class, ParameterMode.REF_CURSOR);
        sp.registerStoredProcedureParameter("p_codigo",         Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_mensaje",          String.class, ParameterMode.OUT);
 
        sp.setParameter("p_id_usuario",    idUsuario);
        sp.setParameter("p_numero_tarjeta", numeroTarjeta);
 
        sp.execute();
 
        Integer codigo  = SpHelper.toInteger(sp.getOutputParameterValue("p_codigo"));
        String  mensaje = SpHelper.toStringValue(sp.getOutputParameterValue("p_mensaje"));
 
        @SuppressWarnings("unchecked")
        List<Object[]> rows = sp.getResultList();
 
        List<CajeroResponse> cajeros = new ArrayList<>();
        for (Object[] row : rows) {
            cajeros.add(CajeroResponse.builder()
                    .idCajero(SpHelper.toInteger(row[0]))
                    .nombreCajero(SpHelper.toStringValue(row[1]))
                    .activo(SpHelper.toBoolean(row[2]))
                    .idBanco(SpHelper.toInteger(row[3]))
                    .nombreBanco(SpHelper.toStringValue(row[4]))
                    .build());
        }
 
        return StoredProcedureResult.<CajeroResponse>builder()
                .codigo(codigo)
                .mensaje(mensaje)
                .objects(cajeros)
                .build();
    }
 
    @Transactional
    public StoredProcedureResult<RetiroResponse> retirar(RetiroRequest request) {
        StoredProcedureQuery sp = entityManager
                .createStoredProcedureQuery("sp_retiro_efectivo");
 
        sp.registerStoredProcedureParameter("p_id_usuario",     Integer.class,    ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_numero_tarjeta",  String.class,    ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_id_cajero",      Integer.class,    ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_monto",          BigDecimal.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_id_retiro",      Integer.class,    ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_codigo",         Integer.class,    ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_mensaje",         String.class,    ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_detalle",          void.class,     ParameterMode.REF_CURSOR);
 
        sp.setParameter("p_id_usuario",    request.getIdUsuario());
        sp.setParameter("p_numero_tarjeta", request.getNumeroTarjeta());
        sp.setParameter("p_id_cajero",     request.getIdCajero());
        sp.setParameter("p_monto",         request.getMonto());
 
        sp.execute();
 
        Integer idRetiro = SpHelper.toInteger(sp.getOutputParameterValue("p_id_retiro"));
        Integer codigo   = SpHelper.toInteger(sp.getOutputParameterValue("p_codigo"));
        String  mensaje  = SpHelper.toStringValue(sp.getOutputParameterValue("p_mensaje"));
 
        @SuppressWarnings("unchecked")
        List<Object[]> rows = sp.getResultList();
 
        List<RetiroDetalleResponse> detalle = new ArrayList<>();
        for (Object[] row : rows) {
            detalle.add(RetiroDetalleResponse.builder()
                    .idRetiro(SpHelper.toInteger(row[0]))
                    .idDenominacion(SpHelper.toInteger(row[1]))
                    .tipo(SpHelper.toStringValue(row[2]))
                    .valorCentavos(SpHelper.toLong(row[3]))
                    .valor(SpHelper.toBigDecimal(row[4]))
                    .cantidad(SpHelper.toInteger(row[5]))
                    .subtotalCentavos(SpHelper.toLong(row[6]))
                    .subtotal(SpHelper.toBigDecimal(row[7]))
                    .build());
        }
 
        RetiroResponse response = RetiroResponse.builder()
                .idRetiro(idRetiro)
                .estado(Integer.valueOf(0).equals(codigo) ? "APROBADO" : "RECHAZADO")
                .mensaje(mensaje)
                .detalle(detalle)
                .build();
 
        return StoredProcedureResult.<RetiroResponse>builder()
                .codigo(codigo)
                .mensaje(mensaje)
                .object(response)
                .build();
    }
 
    @Transactional()
    public StoredProcedureResult<InventarioCajeroResponse> consultarInventario(Integer idCajero) {
        StoredProcedureQuery sp = entityManager
                .createStoredProcedureQuery("sp_consultar_inventario_cajero");
 
        sp.registerStoredProcedureParameter("p_id_cajero",   Integer.class, ParameterMode.IN);
        sp.registerStoredProcedureParameter("p_inventario",   void.class,   ParameterMode.REF_CURSOR);
        sp.registerStoredProcedureParameter("p_codigo",      Integer.class, ParameterMode.OUT);
        sp.registerStoredProcedureParameter("p_mensaje",      String.class, ParameterMode.OUT);
 
        sp.setParameter("p_id_cajero", idCajero);
 
        sp.execute();
 
        Integer codigo  = SpHelper.toInteger(sp.getOutputParameterValue("p_codigo"));
        String  mensaje = SpHelper.toStringValue(sp.getOutputParameterValue("p_mensaje"));
 
        @SuppressWarnings("unchecked")
        List<Object[]> rows = sp.getResultList();
 
        List<InventarioCajeroResponse> inventario = new ArrayList<>();
        for (Object[] row : rows) {
            inventario.add(InventarioCajeroResponse.builder()
                    .idCajero(SpHelper.toInteger(row[0]))
                    .idDenominacion(SpHelper.toInteger(row[1]))
                    .tipo(SpHelper.toStringValue(row[2]))
                    .valorCentavos(SpHelper.toLong(row[3]))
                    .valor(SpHelper.toBigDecimal(row[4]))
                    .cantidadActual(SpHelper.toLong(row[5]))
                    .totalCentavos(SpHelper.toLong(row[6]))
                    .total(SpHelper.toBigDecimal(row[7]))
                    .build());
        }
 
        return StoredProcedureResult.<InventarioCajeroResponse>builder()
                .codigo(codigo)
                .mensaje(mensaje)
                .objects(inventario)
                .build();
    }


}
