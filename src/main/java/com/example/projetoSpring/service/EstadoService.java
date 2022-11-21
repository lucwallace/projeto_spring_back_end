package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.EstadoRepository;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository re;
	
	public Estado find(Integer id) {
		Optional<Estado> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Estado não encontrado"));
	}

}
