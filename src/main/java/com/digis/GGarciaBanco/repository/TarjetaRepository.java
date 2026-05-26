package com.digis.GGarciaBanco.repository;

import com.digis.GGarciaBanco.entity.Tarjeta;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Integer> {

    Optional<Tarjeta> findByNumeroTarjetaAndActivaTrue(String numeroTarjeta);
}
