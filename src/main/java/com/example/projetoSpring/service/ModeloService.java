package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.repositories.ModeloRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class ModeloService {
	
	@Autowired
	private ModeloRepository re;
	
	public Modelo find(Integer id) {
		Optional<Modelo> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("O Modelo do veículo não encontrado! Id: " + id));
	}
	
	public Modelo insert(Modelo obj) {
		obj.setId(null);
		return re.save(obj);
	}

}