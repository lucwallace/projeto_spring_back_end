package com.example.projetoSpring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.dto.MarcaDto;
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
		Marca objComp = re.findByNome(obj.getNome());
		
		if(objComp == null) {
			return re.save(obj);
		} else {
			throw new ObjectNotFoundException("O Marca '"+obj.getNome()+"' já existe.");
		}
	}
	
	public Marca fromDTO(MarcaDto objDto) {
		Marca ma = new Marca(null, objDto.getNome());
		
		return ma;
	}
	
	public Marca update(Marca obj) {
		find(obj.getId());
		return re.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			re.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir modelo.");
		}
	}
	
	public List<Marca> findAll(){
		return re.findAll();
	}
	
	public Page<Marca> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return re.findAll(pageRequest);
	}

}
