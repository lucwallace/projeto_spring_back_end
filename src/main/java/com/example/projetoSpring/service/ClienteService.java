package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Cliente;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.ClienteRepository;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository re;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id + ", Cliente: " + Cliente.class.getName()));
	}

}
