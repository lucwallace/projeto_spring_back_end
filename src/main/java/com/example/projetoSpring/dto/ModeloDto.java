package com.example.projetoSpring.dto;

import java.io.Serializable;

import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.enums.TipoModeloEnum;

public class ModeloDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Double preco;
	
	private Integer marcaId;
	private Integer tipoCarroId;
	private String tipoCarro;
	
	private Integer idTipo;
	
	public ModeloDto() {}
	
	public ModeloDto(Modelo obj) {
		id = obj.getId();
		nome = obj.getNome();
		preco = obj.getPreco();
		tipoCarro = obj.getIdTipo().getDescricao();
		
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipoCarro() {
		return tipoCarro;
	}

	public void setTipoCarro(String tipoCarro) {
		this.tipoCarro = tipoCarro;
	}
	
	
	
	
	
	

}
