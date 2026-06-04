package com.digis.ggarciabanco.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "BANCO")
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BANCO")
    private Integer idBanco;

    @Column(name = "NOMBRE_BANCO")
    private String nombreBanco;

    @Column(name = "ES_PREDETERMINADO")
    private Integer esPredeterminado;

    @Column(name = "ACTIVO")
    private Integer activo;

    @Column(name = "BIN")
    private String bin;
}
