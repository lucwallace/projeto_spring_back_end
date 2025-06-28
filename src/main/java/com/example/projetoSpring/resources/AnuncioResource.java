package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.Anuncio;
import com.example.projetoSpring.domain.StatusAnuncio;
import com.example.projetoSpring.dto.AnuncioRequestDTO;
import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.mapper.AnuncioMapper;
import com.example.projetoSpring.records.AnuncioResponseDTO;
import com.example.projetoSpring.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/anuncio")
public class AnuncioResource {

    @Autowired
    private AnuncioService service;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<AnuncioResponseDTO> find(@PathVariable Integer id) {
        AnuncioResponseDTO obj = service.find(id);

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(obj);
    }

    @GetMapping(value = "/findByStatus/{status}")
    public ResponseEntity<AnuncioResponseDTO> findByStatus(@PathVariable StatusAnuncio status) {
        AnuncioResponseDTO obj = service.findByStatus(status);

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(obj);
    }

    @PostMapping(value = "/criarAnuncio")
    public ResponseEntity<ReturnResponse> insert(@Valid @RequestBody AnuncioRequestDTO objDto) {

        Anuncio obj = service.insert(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Anúncio cadastrado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro do anúncio.");
        }
    }

    @PutMapping(value = "/modificarAnuncio/{id}")
    public ResponseEntity<ReturnResponse> update(@Valid @RequestBody AnuncioRequestDTO objDto, @PathVariable Integer id) {
        objDto.setId(id);
        Anuncio obj = service.update(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Anúncio alterado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer a alteração do anúncio.");
        }
    }

    @DeleteMapping(value = "/deleteAnuncio/{id}")
    public ResponseEntity<Anuncio> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<AnuncioResponseDTO>> findAll() {
        List<Anuncio> listAnuncio = service.findAll();

        List<AnuncioResponseDTO> dtoList = listAnuncio.stream()
                .map(AnuncioMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }


}
