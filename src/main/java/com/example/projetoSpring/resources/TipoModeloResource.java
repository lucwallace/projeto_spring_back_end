package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.dto.MarcaDto;
import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.dto.TipoModeloRequestDTO;
import com.example.projetoSpring.records.MarcaResponseDTO;
import com.example.projetoSpring.records.TipoModeloResponseDTO;
import com.example.projetoSpring.service.MarcaService;
import com.example.projetoSpring.service.TipoModeloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/tiposModelos")
public class TipoModeloResource {

    @Autowired
    private TipoModeloService service;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<TipoModeloResponseDTO> find(@PathVariable Integer id){
        TipoModelo obj = service.find(id);

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        TipoModeloResponseDTO dto = new TipoModeloResponseDTO(obj.getId(), obj.getNome());

        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/criarTipoModelo")
    public ResponseEntity<ReturnResponse> insert(@Valid @RequestBody TipoModeloRequestDTO objDto) {

        TipoModelo obj = service.insert(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Tipo modelo cadastrado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro do tipo do modelo.");
        }
    }

    @PutMapping(value = "/modificarTipoModelo/{id}")
    public ResponseEntity<ReturnResponse> update(@Valid @RequestBody TipoModeloRequestDTO objDto, @PathVariable Integer id){
        objDto.setId(id);
        TipoModelo obj = service.update(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Tipo modelo alterado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer a alteração do tipo do modelo.");
        }
    }

    @DeleteMapping(value = "/deleteTipoModelo/{id}")
    public ResponseEntity<TipoModelo> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<TipoModeloResponseDTO>> findAll() {
        List<TipoModelo> listTipoModelo = service.findAll();

        List<TipoModeloResponseDTO> listTipoModeloResponseDto = listTipoModelo.stream()
                .map(TipoModeloResponseDTO::new)
                .toList();

        return ResponseEntity.ok(listTipoModeloResponseDto);
    }

}
