package com.example.projetoSpring.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @JsonIgnore
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "Modelo_TipoModelo",
            joinColumns = @JoinColumn(name = "tipo_id"),
            inverseJoinColumns = @JoinColumn(name = "modelo_id")
    )
    private List<Modelo> modelosTipo = new ArrayList<>();

}
