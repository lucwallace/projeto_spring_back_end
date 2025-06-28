package com.example.projetoSpring.mapper;

import com.example.projetoSpring.domain.Anuncio;
import com.example.projetoSpring.records.*;

import java.util.stream.Collectors;

public class AnuncioMapper {

    public static AnuncioResponseDTO toDto(Anuncio anuncio){
        return new AnuncioResponseDTO(
                anuncio.getId(),
                anuncio.getPreco(),
                anuncio.getStatus().toString(),
                anuncio.getDescricao(),
                anuncio.getOpcionalAnuncio().stream()
                        .map(o -> new OpcionalResponseDTO(o.getId(), o.getNome()))
                        .collect(Collectors.toList()),
                anuncio.getImagemAnuncios().stream()
                        .map(i -> new ImagemAnuncioResponseDTO(i.getId(), i.getUrl(), i.getPasta(), i.getPublicId()))
                        .collect(Collectors.toList()),
                new ModeloAnuncioResponseDTO(anuncio.getModelo()),
                new VersoesResponseDTO(anuncio.getVersao()),
                new TipoModeloResponseDTO(anuncio.getTipoModelos()),
                anuncio.getAnunciante()
        );
    }

}
