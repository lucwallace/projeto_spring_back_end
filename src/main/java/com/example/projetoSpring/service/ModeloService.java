package com.example.projetoSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.dto.ModeloDto;
import com.example.projetoSpring.enums.TipoModeloEnum;
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
	
	public Modelo fromDTO(ModeloDto objDto) {
		Modelo mo = new Modelo(null, objDto.getNome(), objDto.getPreco(), TipoModeloEnum.toEnum(objDto.getIdTipo()));
		Marca ma = new Marca(objDto.getMarcaId(), null);
		
		mo.getMarcas().add(ma);
		
		return mo;
	}
	
	public Modelo update(Modelo obj) {
		find(obj.getId());
		return re.save(obj);
	}
	
	public void delete(Modelo obj) {
		find(obj.getId());
		try {
			re.delete(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir modelo.");
		}
	}
	

}