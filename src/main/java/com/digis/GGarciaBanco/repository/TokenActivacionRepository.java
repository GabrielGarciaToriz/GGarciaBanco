package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.entity.TokenActivacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenActivacionRepository extends JpaRepository<TokenActivacion, Long> {

    Optional<TokenActivacion> findByTokenAndUsadoFalse(String token);

    @Modifying
    @Query("UPDATE TokenActivacion t SET t.usado = true WHERE t.usuario.idUsuario = :idUsuario AND t.usado = false")
    void invalidarTokensAnteriores(@Param("idUsuario") Integer idUsuario);
}