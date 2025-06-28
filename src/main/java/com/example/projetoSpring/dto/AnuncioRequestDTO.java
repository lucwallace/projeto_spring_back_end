package com.example.projetoSpring.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnuncioRequestDTO {

    private Integer id;
    private Double preco;
    private String status;
    private String descricao;
    List<Integer> idsOpcionais;
    private Integer idModelo;
    private Integer idVersao;
    private Integer idTipoModelo;
}
