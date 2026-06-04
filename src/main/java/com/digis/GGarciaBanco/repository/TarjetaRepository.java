package com.digis.ggarciabanco.repository;

import com.digis.ggarciabanco.entity.Tarjeta;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {

    Optional<Tarjeta> findByUsuario_IdUsuario(Integer idUsuario);

    Optional<Tarjeta> findByNumeroTarjetaAndActivaTrue(String numeroTarjeta);

    Optional<Tarjeta> findByNumeroTarjeta(String numeroTarjeta);
}
