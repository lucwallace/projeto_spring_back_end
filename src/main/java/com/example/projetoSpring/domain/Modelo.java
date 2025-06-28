package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.sql.Timestamp;
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
	private Timestamp dataCriacao;
	private Timestamp dataAlteracao;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "marca_id")
	private Marca marca;

	@OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Versao> versoes = new ArrayList<>();

	@ManyToMany(mappedBy = "modelosTipo")
	@JsonIgnore
	private List<TipoModelo> tipoModelos = new ArrayList<>();

	@OneToMany(mappedBy = "modelo", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonIgnore
	private List<Anuncio> anuncios = new ArrayList<>();

}
