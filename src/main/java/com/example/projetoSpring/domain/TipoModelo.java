package com.example.projetoSpring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TipoModelo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Timestamp dataCriacao;
    private Timestamp dataAlteracao;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Modelo_TipoModelo",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id")
    )
    private List<Modelo> modelosTipo = new ArrayList<>();

    @OneToMany(mappedBy = "tipoModelos", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Anuncio> anuncios = new ArrayList<>();

}
