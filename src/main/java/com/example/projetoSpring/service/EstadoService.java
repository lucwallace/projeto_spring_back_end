package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.EstadoRepository;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository re;
	
	public Estado find(Integer id) {
		Optional<Estado> obj = re.findById(id);
		return obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado"));
	}

	public Estado registroEstado(Estado estado){
		if (estado.getNome() == null || estado.getNome().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do estado não está preenchido.");
		}

		if(re.existsByNome(estado.getNome())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe o estado cadastrado.");
		}

		return re.save(estado);
	}

	public Estado alterarEstado(Integer id, Estado novoEstado){

		Estado estado = re.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado."));

		if (novoEstado.getNome() == null || novoEstado.getNome().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do estado não está preenchido.");
		}

		if(re.existsByNome(novoEstado.getNome())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe o estado cadastrado.");
		}

		return re.save(novoEstado);
	}

	public void deleteEstado(Integer id) {
		Estado estado = re.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estado não encontrado."));

		re.delete(estado);
	}

}
