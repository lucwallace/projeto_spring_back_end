package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Cidade;
import com.example.projetoSpring.domain.Endereco;
import com.example.projetoSpring.repositories.EnderecoRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class EnderecoService {
	
	@Autowired
	private EnderecoRepository re;
	
	public Endereco find(Integer id) {
		Optional<Endereco> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Endereço não encontrada"));
	}

}
