package com.example.projetoSpring.dto;

import java.io.Serializable;

import com.example.projetoSpring.domain.Marca;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MarcaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	private String nome;
	
	public MarcaDto() {}
	
	public MarcaDto(Marca obj) {
		id = obj.getId();
		nome = obj.getNome();
	}

}
