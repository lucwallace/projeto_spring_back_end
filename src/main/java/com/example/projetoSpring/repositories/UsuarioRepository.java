package com.example.projetoSpring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetoSpring.domain.Endereco;
import com.example.projetoSpring.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Endereco obj WHERE obj.id = :id ")
	public Endereco findByEndereco(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO Endereco (id, logradouro, numero, complemento, bairro, cep, usuario_id, cidade_id) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)", nativeQuery = true)
	void insertEndereco(Integer id, String logradouro, String numero, String complemento, String bairro, String cep, Integer usuarioId, Integer cidadeId);
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Endereco obj WHERE obj.id = MAX(obj.id) ")
	public Endereco findByIdMax();
	
	@Transactional(readOnly = true)
	Usuario findByEmail(String email);
	
	@Transactional
	@Query("SELECT obj FROM Usuario obj WHERE obj.id = ?1 AND obj.email = ?2 ")
	Usuario findByEmailId(Integer id, String email);

}
