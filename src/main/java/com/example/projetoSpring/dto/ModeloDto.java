package com.example.projetoSpring.dto;

import java.io.Serializable;

public class ModeloDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private Double preco;
	
	private Integer marcaId;
	private Integer tipoCarroId;
	
	private Integer idTipo;
	
	public ModeloDto() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	public Integer getTipoCarroId() {
		return tipoCarroId;
	}

	public void setTipoCarroId(Integer tipoCarroId) {
		this.tipoCarroId = tipoCarroId;
	}

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}
	
	
	
	

}
