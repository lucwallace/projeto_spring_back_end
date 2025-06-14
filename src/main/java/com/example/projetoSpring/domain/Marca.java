package com.example.projetoSpring.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Marca implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Timestamp dataCriacao;
	private Timestamp dataAlteracao;

	@OneToMany(mappedBy = "marca", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private List<Modelo> modelos = new ArrayList<>();

}
