package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoSpring.domain.Modelo;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {

}
