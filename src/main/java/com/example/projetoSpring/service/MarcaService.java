package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class MarcaService {
	
	@Autowired
	private MarcaRepository re;
	
	public Marca find(Integer id) {
		Optional<Marca> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Marca do veículo não encontrado! Id: " + id + ", Marca: " + Marca.class.getName()));
	}
	
	public Marca insert(Marca obj) {
		obj.setId(null);
		return re.save(obj);
	}

}
