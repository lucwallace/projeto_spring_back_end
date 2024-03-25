package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class TipoCarro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idTipo;
	private String nomeTipo;
	
	public TipoCarro() {}

	public TipoCarro(Integer idTipo, String nomeTipo) {
		super();
		this.idTipo = idTipo;
		this.nomeTipo = nomeTipo;
	}
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "Tipo_Modelo", joinColumns = @JoinColumn(name = "tipo_id"),
	inverseJoinColumns = @JoinColumn(name = "modelo_id"))
	
	private List<Modelo> modelos = new ArrayList<>();

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public String getNomeTipo() {
		return nomeTipo;
	}

	public void setNome(String nomeTipo) {
		this.nomeTipo = nomeTipo;
	}

	public List<Modelo> getModelos() {
		return modelos;
	}

	public void setModelos(List<Modelo> modelos) {
		this.modelos = modelos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idTipo == null) ? 0 : idTipo.hashCode());
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
		TipoCarro other = (TipoCarro) obj;
		return Objects.equals(idTipo, other.idTipo) && Objects.equals(nomeTipo, other.nomeTipo);
	}
	
	

}
