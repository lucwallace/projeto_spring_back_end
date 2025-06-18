package com.example.projetoSpring.mapper;

import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.records.ModeloResponseDTO;
import com.example.projetoSpring.records.TipoModeloResponseDTO;
import com.example.projetoSpring.records.VersoesResponseDTO;

import java.util.stream.Collectors;

public class ModeloMapper {

    public static ModeloResponseDTO toDto(Modelo modelo){
        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getMarca().getNome(),
                modelo.getNome(),
                modelo.getVersoes().stream()
                        .map(v -> new VersoesResponseDTO(v.getId(), v.getNome()))
                        .collect(Collectors.toList()),
                modelo.getTipoModelos().stream()
                        .map(t -> new TipoModeloResponseDTO(t.getId(), t.getNome()))
                        .collect(Collectors.toList())
        );
    }

}
