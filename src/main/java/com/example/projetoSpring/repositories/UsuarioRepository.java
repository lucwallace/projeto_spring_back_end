package com.example.projetoSpring.repositories;

import java.util.Optional;

import com.example.projetoSpring.domain.ERole;
import com.example.projetoSpring.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoSpring.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	//Usuario findByIdUser(Long id);

}
