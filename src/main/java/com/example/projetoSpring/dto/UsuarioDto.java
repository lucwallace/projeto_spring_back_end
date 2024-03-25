package com.example.projetoSpring.dto;

import java.io.Serializable;

import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.service.validation.UsuarioValidacoes;

@UsuarioValidacoes
public class UsuarioDto implements Serializable{
	/*private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull(message = "O nome é de preenchimento obrigatório.")
	@Size(min=3, max=40, message = "O mínimo de caracteres é 3 e o máximo é 40")
	private String nome;
	
	@Email(message = "O E-mail não preenchido no formato correto.")
	private String email;
	
	private Integer tipo;
	
	public UsuarioDto() {}
	
	public UsuarioDto(Usuario obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}*/

}
