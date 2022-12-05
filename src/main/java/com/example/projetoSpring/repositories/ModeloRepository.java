package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetoSpring.domain.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Modelo obj WHERE obj.nome LIKE %:nome% ")
	public Modelo findByNome(@Param("nome") String nome);

}
