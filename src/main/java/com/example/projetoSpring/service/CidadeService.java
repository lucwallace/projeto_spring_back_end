package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Cidade;
import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.CidadeRepository;
import com.example.projetoSpring.repositories.EstadoRepository;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class CidadeService {
	
	@Autowired
	private CidadeRepository re;
	
	public Cidade find(Integer id) {
		Optional<Cidade> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada"));
	}

}
