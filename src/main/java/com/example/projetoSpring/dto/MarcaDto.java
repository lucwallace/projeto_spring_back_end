package com.example.projetoSpring.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.projetoSpring.domain.Marca;

public class MarcaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotNull(message = "O nome é de preenchimento obrigatório.")
	@Size(min=3, max=40, message = "O mínimo de caracteres é 3 e o máximo é 40")
	private String nome;
	
	public MarcaDto() {}
	
	public MarcaDto(Marca obj) {
		id = obj.getId();
		nome = obj.getNome();
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

}
