package com.example.projetoSpring.repositories;

import com.example.projetoSpring.domain.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT obj FROM Modelo obj WHERE obj.nome = :nome ")
    Optional<Modelo> findByNome(@Param("nome") String nome);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO marca_modelo (marca_id, modelo_id) VALUES (?1, ?2)", nativeQuery = true)
    void insertModeloMarca(Integer idMarca, Integer idModelo);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT m FROM Modelo m " +
            "JOIN m.tipoModelos t " +
            "WHERE m.id = :idModelo AND t.id = :tipoId")
    Optional<Modelo> findByModeloAndTipoModeloById(@Param("idModelo") Integer idModelo, @Param("tipoId") Integer tipoId);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT m FROM Modelo m " +
            "JOIN m.tipoModelos t " +
            "WHERE m.nome = :nomeModelo AND t.nome = :nomeTipo")
    Optional<Modelo> findByModeloAndTipoModeloByNome(@Param("nomeModelo") String nomeModelo, @Param("nomeTipo") String nomeTipo);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM Modelo m " +
            "JOIN m.marca marca " +
            "WHERE m.nome = :nomeModelo AND marca.nome = :nomeMarca")
    Optional<Modelo> findByModeloAndMarcaByNome(@Param("nomeModelo") String nomeModelo,
                                                  @Param("nomeMarca") String nomeMarca);

    @Transactional(readOnly = true)
    @Query("SELECT m FROM Modelo m " +
            "JOIN m.marca marca " +
            "WHERE m.id = :idModelo AND marca.id = :idMarca")
    Optional<Modelo> findByModeloAndMarcaById(@Param("idModelo") Integer idModelo,
                                                @Param("idMarca") Integer idMarca);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT m FROM Modelo m " +
            "JOIN m.tipoModelos t " +
            "JOIN m.versoes v " +
            "WHERE m.id = :idModelo AND t.id = :idTipo AND v.id = :idVersao")
    Optional<Modelo> findByModeloAndTipoModeloAndVersaoById(
            @Param("idModelo") Integer idModelo,
            @Param("idTipo") Integer idTipo,
            @Param("idVersao") Integer idVersao);

}
