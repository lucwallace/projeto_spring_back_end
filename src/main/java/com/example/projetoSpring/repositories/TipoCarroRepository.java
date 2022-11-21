package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoSpring.domain.TipoCarro;

@Repository
public interface TipoCarroRepository extends JpaRepository<TipoCarro, Integer>{
	
	

}