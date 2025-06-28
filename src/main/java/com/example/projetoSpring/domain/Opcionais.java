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
public class Opcionais {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Timestamp dataCriacao;
    private Timestamp dataAlteracao;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "Anuncio_Opcional",
            joinColumns = @JoinColumn(name = "opcional_id"),
            inverseJoinColumns = @JoinColumn(name = "anuncio_id")
    )
    private List<Anuncio> anuncioOpcional = new ArrayList<>();
}
