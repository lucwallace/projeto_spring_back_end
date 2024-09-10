package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.ImagemPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImagemPerfilRepository extends JpaRepository<ImagemPerfil, String> {

    @Query(value = "SELECT DISTINCT * FROM imagem_perfil obj WHERE obj.user_id = ?1", nativeQuery = true)
    public ImagemPerfil findByIdUser(Long idUser);

}
