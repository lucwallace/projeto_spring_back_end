package com.example.projetoSpring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Endereco;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.UsuarioDto;
import com.example.projetoSpring.enums.TipoClienteEnum;
import com.example.projetoSpring.repositories.EnderecoRepository;
import com.example.projetoSpring.repositories.UsuarioRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository re;
	
	@Autowired
	private EnderecoRepository reEnd;
	
	public Usuario find(Integer id) {
		Optional<Usuario> obj = re.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado! Id: " + id + ", Cliente: " + Usuario.class.getName()));
	}
	
	public Usuario insert(Usuario obj) {
		obj.setId(null);
		//Usuario objComp = re.findByNome(obj.getNome());
		
		//if(objComp == null) {
			return re.save(obj);
		//} else {
		//	throw new ObjectNotFoundException("O Marca '"+obj.getNome()+"' já existe.");
		//}
	}
	
	public Usuario fromDTO(UsuarioDto objDto) {
		Usuario user = new Usuario(null, objDto.getNome(), objDto.getEmail(), TipoClienteEnum.toEnum(objDto.getIdTipo()));
		//Endereco ed = new Endereco(objDto.getEnderecoId(), null, null, null, null, null, null);
		
		//user.setEnderecos(ed);
		
		return user;
	}
	
	public Usuario insertUsuarioEndereco(UsuarioDto objDto, Integer idUsuario) {
		Usuario user = new Usuario(null, objDto.getNome(), objDto.getEmail(), TipoClienteEnum.toEnum(objDto.getIdTipo()));
		//Endereco ed = new Endereco(objDto.getEnderecoId(), null, null, null, null, null, null);
		
		//user.setEnderecos(ed);
		
		re.insertUsuarioEndereco(idUsuario, objDto.getEnderecoId());
		
		return user;
	}
	
	public Usuario update(Usuario obj) {
		find(obj.getId());
		return re.save(obj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			re.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Não é possivel excluir o usuário.");
		}
	}
	
	public List<Usuario> findAll(){
		return re.findAll();
	}
	
	public Page<Usuario> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return re.findAll(pageRequest);
	}
	
	public Endereco insertEndereco(UsuarioDto objDto, Usuario usuario) {
		
		Endereco edS = new Endereco();
		
		int idUltimo;
		
		List<Endereco> edM = new ArrayList<>();
		
		edS = re.findByEndereco(objDto.getEnderecoId());
		
		edM = reEnd.findAll();
		
		idUltimo = edM.size() + 1;
		
		//edS.setUsuario(usuario);
		
		re.insertEndereco(idUltimo, edS.getLogradouro(), edS.getNumero(), edS.getComplemento(), edS.getBairro(), edS.getCep(), usuario.getId(), edS.getCidade().getId());
		
		return null;
	}

}
