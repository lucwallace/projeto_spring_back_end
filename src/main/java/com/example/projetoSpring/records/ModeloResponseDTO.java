package com.example.projetoSpring.records;

import com.example.projetoSpring.dto.MarcaDto;

import java.util.List;

public record ModeloResponseDTO(Integer id,
                                String nomeMarca,
                                String nome,
                                List<VersoesResponseDTO> versoes,
                                List<TipoModeloResponseDTO> tipoModelos) {
}
