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
public class Anuncio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Double preco;
    @Enumerated(EnumType.STRING)
    private StatusAnuncio status;
    private Timestamp dataCriacao;
    private Timestamp dataAlteracao;
    private String descricao;

    @ManyToMany(mappedBy = "anuncioOpcional")
    @JsonIgnore
    private List<Opcionais> opcionalAnuncio = new ArrayList<>();

    @OneToMany(mappedBy = "anuncio", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnore
    private List<ImagemAnuncio> imagemAnuncios = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @ManyToOne
    @JoinColumn(name = "versao_id")
    private Versao versao;

    @ManyToOne
    @JoinColumn(name = "tipoModelo_id", nullable = false)
    private TipoModelo tipoModelos;

    @ManyToOne
    @JoinColumn(name = "anunciante_id", nullable = false)
    private Anunciante anunciante;
}
