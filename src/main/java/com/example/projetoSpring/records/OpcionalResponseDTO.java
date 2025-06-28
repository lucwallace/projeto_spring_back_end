package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Opcionais;
import com.example.projetoSpring.domain.TipoModelo;

public record OpcionalResponseDTO(Integer id, String nome) {

    public OpcionalResponseDTO(Opcionais opcionais) {
        this(opcionais.getId(), opcionais.getNome());
    }
}
