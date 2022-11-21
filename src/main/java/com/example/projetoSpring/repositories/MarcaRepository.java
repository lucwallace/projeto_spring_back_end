package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoSpring.domain.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer>{
	

}
