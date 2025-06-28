package com.example.projetoSpring.records;

import com.example.projetoSpring.domain.Anunciante;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.domain.Versao;

import java.util.List;

public record AnuncioResponseDTO(Integer id,
                                 Double preco,
                                 String status,
                                 String descricao,
                                 List<OpcionalResponseDTO> listaOpcionais,
                                 List<ImagemAnuncioResponseDTO> listaImagensAnuncios,
                                 ModeloAnuncioResponseDTO modelo,
                                 VersoesResponseDTO versao,
                                 TipoModeloResponseDTO tipoModelo,
                                 Anunciante anunciante) {
}
