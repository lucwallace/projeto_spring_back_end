package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.TipoCarro;
import com.example.projetoSpring.repositories.TipoCarroRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class TipoCarroService {
	
	@Autowired
	private TipoCarroRepository re;
	
	public TipoCarro find(Integer id) {
		Optional<TipoCarro> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("O Tipo do veículo não encontrado! Id: " + id));
	}
	
	public TipoCarro insert(TipoCarro obj) {
		obj.setIdTipo(null);
		return re.save(obj);
	}

}
