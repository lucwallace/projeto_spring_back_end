package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Anunciante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface AnuncianteRepository extends JpaRepository<Anunciante, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT a FROM Anunciante a " +
            "WHERE a.uuid_usuario = :uuid_usuario")
    Anunciante findAnuncianteByUUID(
            @Param("uuid_usuario") UUID uuid_usuario);

}
