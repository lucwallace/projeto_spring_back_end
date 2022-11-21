package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.example.projetoSpring.enums.TipoClienteEnum;
import com.example.projetoSpring.enums.TipoModeloEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Modelo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	private Integer idTipo;
	
	public Modelo() {}

	public Modelo(Integer id, String nome, Double preco, TipoModeloEnum idTipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.idTipo = idTipo.getCodigo();
	}
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "Marca_Modelo", joinColumns = @JoinColumn(name = "modelo_id"),
	inverseJoinColumns = @JoinColumn(name = "marca_id"))
	
	private List<Marca> marcas = new ArrayList<>(); 
	
	@ManyToMany(mappedBy = "modelos")
	private List<TipoCarro> tipoCarros = new ArrayList<>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	
	public List<TipoCarro> getTipoCarros() {
		return tipoCarros;
	}

	public void setTipoCarros(List<TipoCarro> tipoCarros) {
		this.tipoCarros = tipoCarros;
	}

	public TipoModeloEnum getIdTipo() {
		return TipoModeloEnum.toEnum(idTipo);
	}

	public void setIdTipo(TipoModeloEnum idTipo) {
		this.idTipo = idTipo.getCodigo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Modelo other = (Modelo) obj;
		return Objects.equals(id, other.id) && Objects.equals(nome, other.nome) && Objects.equals(preco, other.preco) && Objects.equals(idTipo, other.idTipo);
	}
	
	
	
	
	
	

}
