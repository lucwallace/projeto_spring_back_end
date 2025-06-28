package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.ImagemAnuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagemAnuncianteRepository extends JpaRepository<ImagemAnuncio, Integer> {
}
