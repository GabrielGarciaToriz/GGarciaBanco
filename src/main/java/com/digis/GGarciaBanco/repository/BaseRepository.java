package com.digis.GGarciaBanco.repository;

import java.util.Map;

import org.hibernate.dialect.OracleTypes;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

public class BaseRepository {

    protected final JdbcTemplate jdbc;

    protected BaseRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    protected static SqlParameter in(String name, int sqlType) {
        return new SqlParameter(name, sqlType);
    }

    protected static SqlOutParameter out(String name, int sqlType) {
        return new SqlOutParameter(name, sqlType);
    }

    protected static SqlOutParameter outCursor(String name) {
        return new SqlOutParameter(name, OracleTypes.CURSOR);
    }

    protected int codigo(Map<String, Object> r) {
        Object v = r.get("p_codigo");
        return v != null ? ((Number) v).intValue() : 0;
    }

    protected String mensaje(Map<String, Object> r) {
        Object v = r.get("p_mensaje");
        return v != null ? v.toString() : "";
    }

}
