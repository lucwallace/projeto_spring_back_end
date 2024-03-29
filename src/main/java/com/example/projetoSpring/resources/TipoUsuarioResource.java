package com.example.projetoSpring.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.projetoSpring.domain.TipoCarro;
import com.example.projetoSpring.domain.TipoUsuario;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.UsuarioDto;
import com.example.projetoSpring.service.TipoCarroService;
import com.example.projetoSpring.service.TipoUsuarioService;

@RestController
@RequestMapping(value="/tipoUsuario")
public class TipoUsuarioResource {

	@Autowired
	private TipoUsuarioService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		TipoUsuario obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody TipoUsuario obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
			.path("/{id}").buildAndExpand(obj.getIdTipo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<TipoUsuario>> findAll() {
		List<TipoUsuario> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	

}
