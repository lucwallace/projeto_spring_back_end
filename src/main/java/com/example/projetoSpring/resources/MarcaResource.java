package com.example.projetoSpring.resources;

import java.util.List;

import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.records.MarcaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.dto.MarcaDto;
import com.example.projetoSpring.service.MarcaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/marcas")
public class MarcaResource {

	@Autowired
	private MarcaService service;
	
	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<MarcaResponseDTO> find(@PathVariable Integer id){
		Marca obj = service.find(id);

		if (obj == null) {
			return ResponseEntity.notFound().build();
		}

		MarcaResponseDTO dto = new MarcaResponseDTO(obj.getId(), obj.getNome());

		return ResponseEntity.ok(dto);
	}
	
	@PostMapping(value = "/criarMarca")
	public ResponseEntity<ReturnResponse> insert(@Valid @RequestBody MarcaDto objDto) {

		Marca obj = service.insert(objDto);

		ReturnResponse returnResponse = new ReturnResponse();
		if (obj != null && obj.getId() != null) {
			returnResponse.setMessaje("Marca cadastrada com sucesso.");
			return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro da marca.");
		}
	}
	
	@PutMapping(value = "/modificarMarca/{id}")
	public ResponseEntity<ReturnResponse> update(@Valid @RequestBody MarcaDto objDto, @PathVariable Integer id){
		objDto.setId(id);
		Marca obj = service.update(objDto);

		ReturnResponse returnResponse = new ReturnResponse();
		if (obj != null && obj.getId() != null) {
			returnResponse.setMessaje("Marca alterada com sucesso.");
			return ResponseEntity.status(HttpStatus.OK).body(returnResponse);
		} else {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer a alteração da marca.");
		}
	}
	
	@DeleteMapping(value = "/deletaMarca/{id}")
	public ResponseEntity<Marca> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/findAll")
	public ResponseEntity<List<MarcaResponseDTO>> findAll() {
		List<Marca> listMarca = service.findAll();

		List<MarcaResponseDTO> listMarcaResponseDto = listMarca.stream()
				.map(MarcaResponseDTO::new)
				.toList();

		return ResponseEntity.ok(listMarcaResponseDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<MarcaDto>> findPage( 
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction){
		
		Page<Marca> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<MarcaDto> listDto = list.map(obj -> new MarcaDto(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
}
