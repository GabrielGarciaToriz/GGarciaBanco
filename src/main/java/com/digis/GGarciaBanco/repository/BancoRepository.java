/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.digis.ggarciabanco.repository;

import com.digis.ggarciabanco.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author ALIEN59
 */
public interface BancoRepository extends JpaRepository<Banco, Integer>{
    
}
