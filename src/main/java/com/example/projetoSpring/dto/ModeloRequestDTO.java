package com.example.projetoSpring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ModeloRequestDTO {

    private Integer id;
    private String nome;
    private Integer idMarca;
    private String nomeVersao;
    private List<Integer> idsTipoModelo;

}
