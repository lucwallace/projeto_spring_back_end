package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Marca;

public record MarcaResponseDTO(Integer id, String nome) {
    public MarcaResponseDTO(Marca marca) {
        this(marca.getId(), marca.getNome());
    }
}
