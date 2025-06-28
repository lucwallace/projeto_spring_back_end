package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Versao;

public record VersoesResponseDTO(
        Integer id,
        String nome) {

    public VersoesResponseDTO(Versao versao){
        this(versao.getId(), versao.getNome());
    }
}
