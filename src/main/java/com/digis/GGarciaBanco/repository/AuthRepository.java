package com.digis.ggarciabanco.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthRepository extends BaseRepository {

    public AuthRepository(JdbcTemplate jdbc) {
        super(jdbc);
    }

  

}
