package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Modelo;

import java.util.List;
import java.util.stream.Collectors;

public record ModeloResponseDTO(Integer id,
                                String nomeMarca,
                                String nome,
                                List<VersoesResponseDTO> versoes,
                                List<TipoModeloResponseDTO> tipoModelos) {

    public ModeloResponseDTO(Modelo modelo) {
        this(modelo.getId(),
                modelo.getMarca().getNome(),
                modelo.getNome(),
                modelo.getVersoes().stream()
                        .map(v -> new VersoesResponseDTO(v.getId(), v.getNome()))
                        .collect(Collectors.toList()),
                modelo.getTipoModelos().stream()
                        .map(t -> new TipoModeloResponseDTO(t.getId(), t.getNome()))
                        .collect(Collectors.toList()));
    }
}
