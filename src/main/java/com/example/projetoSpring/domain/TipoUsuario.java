package com.example.projetoSpring.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TipoUsuario {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idTipo;
	private String nomeTipo;
	
	public TipoUsuario() {}

	public TipoUsuario(Integer idTipo, String nomeTipo) {
		super();
		this.idTipo = idTipo;
		this.nomeTipo = nomeTipo;
	}

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
		TipoUsuario other = (TipoUsuario) obj;
		return Objects.equals(idTipo, other.idTipo) && Objects.equals(nomeTipo, other.nomeTipo);
	}
}
