package com.digis.GGarciaBanco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;

    @Column(name = "PUBLIC_ID")
    private String publicId;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name = "CORREO")
    private String correo;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACTIVO")
    private Boolean activo;

    @Column(name = "FECHA_REGISTRO")
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "usuario")
    @ToString.Exclude
    private List<Tarjeta> tarjetas;
}
