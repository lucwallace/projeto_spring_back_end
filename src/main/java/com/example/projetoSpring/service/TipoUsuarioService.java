package com.example.projetoSpring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.TipoUsuario;
import com.example.projetoSpring.repositories.TipoUsuarioRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class TipoUsuarioService {
	
	@Autowired
	private TipoUsuarioRepository re;
	
	public TipoUsuario find(Integer id) {
		Optional<TipoUsuario> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("O Tipo do veículo não encontrado! Id: " + id));
	}
	
	public TipoUsuario insert(TipoUsuario obj) {
		obj.setIdTipo(null);
		return re.save(obj);
	}
	
	public List<TipoUsuario> findAll(){
		return re.findAll();
	}

}
