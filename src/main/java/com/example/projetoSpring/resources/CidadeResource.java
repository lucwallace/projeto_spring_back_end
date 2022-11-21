package com.example.projetoSpring.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoSpring.domain.Cidade;
import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.service.CidadeService;
import com.example.projetoSpring.service.EstadoService;
import com.example.projetoSpring.service.MarcaService;

@RestController
@RequestMapping(value="/cidades")
public class CidadeResource {

	@Autowired
	private CidadeService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id){
		Cidade obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	
	

}
