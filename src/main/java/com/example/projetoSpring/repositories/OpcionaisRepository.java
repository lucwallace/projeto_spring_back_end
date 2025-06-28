package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Opcionais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OpcionaisRepository extends JpaRepository <Opcionais, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT obj FROM Opcionais obj WHERE obj.nome LIKE %:nome% ")
    public Opcionais findByNome(@Param("nome") String nome);

}
