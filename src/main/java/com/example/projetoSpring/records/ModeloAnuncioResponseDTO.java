package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Modelo;

import java.util.stream.Collectors;

public record ModeloAnuncioResponseDTO(Integer id,
                                       String nomeMarca,
                                       String nome) {

    public ModeloAnuncioResponseDTO(Modelo modelo) {
        this(modelo.getId(),
                modelo.getMarca().getNome(),
                modelo.getNome());
    }
}
