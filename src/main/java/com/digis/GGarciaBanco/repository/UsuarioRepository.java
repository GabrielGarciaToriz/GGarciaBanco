package com.digis.ggarciabanco.repository;

import com.digis.ggarciabanco.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Procedure(procedureName = "AGREGAR_USUARIO")
    Integer agregarUsuario(
            @Param("p_public_id") String publicId,
            @Param("p_nombre") String nombre,
            @Param("p_apellido_paterno") String apellidoPaterno,
            @Param("p_apellido_materno") String apellidoMaterno,
            @Param("p_correo") String correo,
            @Param("p_password_hash") String password, // <-- Corrección aquí
            @Param("p_id_banco") Integer idBanco,
            @Param("p_numero_tarjeta") String numeroTarjeta);

    Optional<Usuario> findByPublicId(String publicId);

    Optional<Usuario> findByCorreo(String correo);

    @Procedure(procedureName = "ACTIVAR_USUARIO")
    Integer activarUsuario(@Param("p_id_usuario") Integer idUsuario);

}
