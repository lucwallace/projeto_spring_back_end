package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.TipoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TipoModeloRepository extends JpaRepository<TipoModelo, Integer> {

    @Transactional(readOnly=true)
    @Query("SELECT DISTINCT obj FROM TipoModelo obj WHERE obj.nome LIKE %:nome% ")
    public TipoModelo findByNome(@Param("nome") String nome);

}
