package com.digis.ggarciabanco.repository;

import com.digis.ggarciabanco.dto.retiro.HistorialMovimientoResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class HistorialMovimientoResponseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<HistorialMovimientoResponse> obtenerHistorialPorPublicId(String publicId) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_historial_movimientos_publicid");

        query.registerStoredProcedureParameter("p_public_id", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cursor", void.class, ParameterMode.REF_CURSOR);
        query.setParameter("p_public_id", publicId);
        query.execute();

        List<Object[]> rows = query.getResultList();
        List<HistorialMovimientoResponse> historial = new ArrayList<>();

        for (Object[] row : rows) {
            HistorialMovimientoResponse item = HistorialMovimientoResponse.builder()
                    .identificador(toLong(row[0]))
                    .registro(toString(row[1]))
                    .nombre(toString(row[2]))
                    .apellidoPaterno(toString(row[3]))
                    .apellidoMaterno(toString(row[4]))
                    .tarjetaUsada(toString(row[5]))
                    .nombreBanco(toString(row[6]))
                    .idRetiro(toLong(row[7]))
                    .montoPesosSolicitado(toBigDecimal(row[8]))
                    .totalEntregado(toBigDecimal(row[9]))
                    .fecha(toString(row[10]))
                    .estado(toString(row[11]))
                    .build();
            historial.add(item);
        }
        return historial;

    }

    private Long toLong(Object value) {
        if (value == null) {
            return null;
        }

        return ((Number) value).longValue();
    }

    private BigDecimal toBigDecimal(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal;
        }

        return new BigDecimal(value.toString());
    }

    private String toString(Object value) {
        if (value == null) {
            return null;
        }

        return value.toString();
    }

   private LocalDateTime toLocalDateTime(Object value) {
    if (value == null) {
        return null;
    }

    if (value instanceof LocalDateTime localDateTime) {
        return localDateTime;
    }

    if (value instanceof Timestamp timestamp) {
        return timestamp.toLocalDateTime();
    }

    if (value instanceof Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    return null;
}
}
