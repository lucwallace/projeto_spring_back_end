package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.ImagemPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagemPerfilRepository extends JpaRepository<ImagemPerfil, String> {
    ;
}
