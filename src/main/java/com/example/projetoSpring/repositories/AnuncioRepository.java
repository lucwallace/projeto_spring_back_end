package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Anuncio;
import com.example.projetoSpring.domain.StatusAnuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AnuncioRepository extends JpaRepository<Anuncio, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Anuncio obj WHERE obj.status = :status ")
    Optional<Anuncio> findByStatus(@Param("status") StatusAnuncio status);

}
