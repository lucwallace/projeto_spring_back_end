package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.Opcionais;
import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.dto.OpcionalRequestDTO;
import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.dto.TipoModeloRequestDTO;
import com.example.projetoSpring.records.OpcionalResponseDTO;
import com.example.projetoSpring.records.TipoModeloResponseDTO;
import com.example.projetoSpring.service.OpcionalService;
import com.example.projetoSpring.service.TipoCarroService;
import com.example.projetoSpring.service.TipoModeloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/opcional")
public class OpcionalResource {

    @Autowired
    private OpcionalService service;

    @GetMapping(value = "/findById/{id}")
    public ResponseEntity<OpcionalResponseDTO> find(@PathVariable Integer id) {
        Opcionais obj = service.find(id);

        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        OpcionalResponseDTO dto = new OpcionalResponseDTO(obj.getId(), obj.getNome());

        return ResponseEntity.ok(dto);
    }

    @PostMapping(value = "/criarOpcional")
    public ResponseEntity<ReturnResponse> insert(@Valid @RequestBody OpcionalRequestDTO objDto) {

        Opcionais obj = service.insert(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Opcional cadastrado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro do opcional.");
        }
    }

    @PutMapping(value = "/modificarOpcional/{id}")
    public ResponseEntity<ReturnResponse> update(@Valid @RequestBody OpcionalRequestDTO objDto, @PathVariable Integer id) {
        objDto.setId(id);
        Opcionais obj = service.update(objDto);

        ReturnResponse returnResponse = new ReturnResponse();
        if (obj != null && obj.getId() != null) {
            returnResponse.setMessaje("Opcional alterado com sucesso.");
            return ResponseEntity.status(HttpStatus.OK).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer a alteração do opcional.");
        }
    }

    @DeleteMapping(value = "/deleteOpcional/{id}")
    public ResponseEntity<Opcionais> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/findAll")
    public ResponseEntity<List<OpcionalResponseDTO>> findAll() {
        List<Opcionais> listOpcionais = service.findAll();

        List<OpcionalResponseDTO> listOpcionalResponseDTO = listOpcionais.stream()
                .map(OpcionalResponseDTO::new)
                .toList();

        return ResponseEntity.ok(listOpcionalResponseDTO);
    }


}
