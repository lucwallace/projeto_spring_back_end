package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetoSpring.domain.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{
	
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Marca obj WHERE obj.nome LIKE %:nome% ")
	public Marca findByNome(@Param("nome") String nome);

}
