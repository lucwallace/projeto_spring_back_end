package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Versao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface VersaoRepository extends JpaRepository<Versao, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Versao obj WHERE obj.nome LIKE %:nome% ")
    public Versao findByNome(@Param("nome") String nome);

    @Query("SELECT v FROM Versao v " +
            "JOIN v.modelo m " +
            "JOIN m.marca a " +
            "WHERE LOWER(v.nome) = LOWER(:nomeVersao) " +
            "AND LOWER(m.nome) = LOWER(:nomeModelo) " +
            "AND a.id = :idMarca")
    Optional<Versao> findVersaoAndModeloByNome(@Param("nomeVersao") String nomeVersao,
                                               @Param("nomeModelo") String nomeModelo,
                                               @Param("idMarca") Integer idMarca);

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Versao v " +
            "JOIN v.modelo m " +
            "WHERE v.id = :idVersao AND m.id = :idModelo")
    Optional<Versao> findVersaoAndModeloById(@Param("idVersao") String idVersao,
                                               @Param("idModelo") String idModelo);

}
