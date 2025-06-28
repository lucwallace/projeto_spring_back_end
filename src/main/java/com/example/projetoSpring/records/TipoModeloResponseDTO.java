package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.TipoModelo;

public record TipoModeloResponseDTO(Integer id, String nome) {
    public TipoModeloResponseDTO(TipoModelo tipoModelo) {
        this(tipoModelo.getId(), tipoModelo.getNome());
    }
}
