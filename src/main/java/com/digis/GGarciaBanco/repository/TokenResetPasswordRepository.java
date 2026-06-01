package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.entity.TokenResetPassword;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TokenResetPasswordRepository
        extends JpaRepository<TokenResetPassword, Integer> {

    Optional<TokenResetPassword> findByToken(String token);

    @Modifying
    @Query("UPDATE TokenResetPassword t SET t.usado = 1 " +
           "WHERE t.usuario.idUsuario = :idUsuario AND t.usado = 0")
    void invalidarTokensPendientes(Integer idUsuario);

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END " +
           "FROM TokenResetPassword t " +
           "WHERE t.token = :token " +
           "AND t.usado = 0 " +
           "AND t.fechaExpiracion > CURRENT_TIMESTAMP")
    boolean existeTokenValido(String token);
}