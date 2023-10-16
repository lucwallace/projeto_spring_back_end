package com.example.projetoSpring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.dto.ModeloDto;
import com.example.projetoSpring.service.ModeloService;

@RestController
@RequestMapping(value="/modelos")
public class ModeloResource {

	@Autowired
	private ModeloService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Modelo> find(@PathVariable Integer id){
		Modelo obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ModeloDto objDto) {
		Modelo obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		obj = service.insertMarcaModelo(objDto, obj.getId());
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ModeloDto objDto, @PathVariable Integer id){
		Modelo obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Modelo> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ModeloDto>> findAll() {
		List<Modelo> list = service.findAll();
		List<ModeloDto> listDto = list.stream().map(obj -> new ModeloDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ModeloDto>> findPage( 
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction){
		
		Page<Modelo> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ModeloDto> listDto = list.map(obj -> new ModeloDto(obj));
		return ResponseEntity.ok().body(listDto);
	}
	

}
