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

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.MarcaDto;
import com.example.projetoSpring.dto.UsuarioDto;
import com.example.projetoSpring.service.UsuarioService;

@RestController
@RequestMapping(value="/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Usuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody UsuarioDto objDto) {
		Usuario obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		service.insertEndereco(objDto, obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		obj = service.insertUsuarioEndereco(objDto, obj.getId());
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody UsuarioDto objDto, @PathVariable Integer id){
		Usuario obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Usuario> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<UsuarioDto>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDto> listDto = list.stream().map(obj -> new UsuarioDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<UsuarioDto>> findPage( 
		@RequestParam(value="page", defaultValue="0") Integer page,
		@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
		@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
		@RequestParam(value="direction", defaultValue="ASC") String direction){
		
		Page<Usuario> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<UsuarioDto> listDto = list.map(obj -> new UsuarioDto(obj));
		return ResponseEntity.ok().body(listDto);
	}
	
}
