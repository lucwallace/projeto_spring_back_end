package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import com.example.projetoSpring.enums.TipoModeloEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Modelo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco; //colocar no anuncio
	private Integer idTipo; //Ainda vendo

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(
			name = "Marca_Modelo",
			joinColumns = @JoinColumn(name = "modelo_id"),
			inverseJoinColumns = @JoinColumn(name = "marca_id")
	)
	private List<Marca> marcas = new ArrayList<>();

	@OneToMany(mappedBy = "modelo", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonIgnore
	private List<Versao> versoes = new ArrayList<>();

	@OneToMany(mappedBy = "modelosTipo", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonIgnore
	private List<TipoModelo> tipoModelos = new ArrayList<>();

}
