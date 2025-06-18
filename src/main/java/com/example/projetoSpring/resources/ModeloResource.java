package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.dto.*;
import com.example.projetoSpring.mapper.ModeloMapper;
import com.example.projetoSpring.records.MarcaResponseDTO;
import com.example.projetoSpring.records.ModeloResponseDTO;
import com.example.projetoSpring.records.TipoModeloResponseDTO;
import com.example.projetoSpring.service.ModeloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/modelos")
public class ModeloResource {

    @Autowired
    private ModeloService service;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<ModeloResponseDTO> find(@PathVariable Integer id){
        ModeloResponseDTO obj = service.find(id);

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(obj);
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<ModeloResponseDTO>> findAll() {
        List<Modelo> listModelo = service.findAll();

        List<ModeloResponseDTO> dtoList = listModelo.stream()
                .map(ModeloMapper::toDto)
                .toList();

        return ResponseEntity.ok(dtoList);
    }

    @PostMapping(value = "/criarModelo")
    public ResponseEntity<ReturnResponse> insert(@Valid @RequestBody ModeloRequestDTO objDto) {

        Modelo obj = service.insert(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Modelo cadastrado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro do modelo.");
        }
    }

    @PutMapping(value = "/modificarModelo/{id}")
    public ResponseEntity<ReturnResponse> update(@Valid @RequestBody ModeloRequestDTO objDto, @PathVariable Integer id){
        objDto.setId(id);
        Modelo obj = service.update(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Modelo alterado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer a alteração do modelo.");
        }
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ModeloDto>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {

        Page<Modelo> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ModeloDto> listDto = list.map(obj -> new ModeloDto(obj));
        return ResponseEntity.ok().body(listDto);
    }

    @DeleteMapping(value = "/deletaModelo/{id}")
    public ResponseEntity<Modelo> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
