package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	@ManyToOne
	@JoinColumn(name="estado_id")
	@JsonBackReference
	private Estado estado;

	@OneToMany(mappedBy = "cidade", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Endereco> enderecos = new ArrayList<>();

}
