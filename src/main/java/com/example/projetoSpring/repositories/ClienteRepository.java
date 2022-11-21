package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoSpring.domain.Cidade;
import com.example.projetoSpring.domain.Cliente;
import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
	

}
