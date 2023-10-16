package com.example.projetoSpring.service;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Cidade;
import com.example.projetoSpring.domain.Endereco;
import com.example.projetoSpring.domain.Estado;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.enums.TipoClienteEnum;
import com.example.projetoSpring.enums.TipoModeloEnum;
import com.example.projetoSpring.repositories.CidadeRepository;
import com.example.projetoSpring.repositories.EnderecoRepository;
import com.example.projetoSpring.repositories.EstadoRepository;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.repositories.ModeloRepository;
import com.example.projetoSpring.repositories.TipoCarroRepository;
import com.example.projetoSpring.repositories.UsuarioRepository;

@Service
public class DBService {
	
	@Autowired
	private MarcaRepository marcaRepository;
	
	@Autowired
	private ModeloRepository modeloRepository;
	
	@Autowired
	private TipoCarroRepository tipoCarroRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private UsuarioRepository clienteRepository;

	public void instantiateTestDatabase() throws ParseException{
		
		Marca mac1 = new Marca(null, "Fiat");
		Marca mac2 = new Marca(null, "Chevrolet");
		
		Modelo m1 = new Modelo(null, "Palio", 22.300, TipoModeloEnum.HATCH);
		Modelo m2 = new Modelo(null, "Corsa", 18.900, TipoModeloEnum.HATCH);
		
		Estado est1 = new Estado(null, "São Paulo");
		Estado est2 = new Estado(null, "Minas Gerais");
		
		Cidade c1 = new Cidade(null, "São Paulo", est1);
		Cidade c2 = new Cidade(null, "Belo Horizonte", est2);
		Cidade c3 = new Cidade(null, "Campinas", est1);
		
		//TipoCarro t1 = new TipoCarro(null, "Hatch");
		
		mac1.getModelos().addAll(Arrays.asList(m1));
		mac2.getModelos().addAll(Arrays.asList(m2));
		
		m1.getMarcas().addAll(Arrays.asList(mac1));
		m2.getMarcas().addAll(Arrays.asList(mac2));
		
		//m1.getTipoCarros().addAll(Arrays.asList(t1));
		//m2.getTipoCarros().addAll(Arrays.asList(t1));
		
		//t1.getModelos().addAll(Arrays.asList(m1));
		//t1.getModelos().addAll(Arrays.asList(m2));
		
		est1.getCidades().addAll(Arrays.asList(c1, c3));
		est2.getCidades().addAll(Arrays.asList(c2));
		
		Usuario cli1 = new Usuario(null, "Maria Silva", "maria@gmail.com", TipoClienteEnum.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", c2);

		//cli1.getEnderecos().addAll(Arrays.asList(e1));
		
		cli1.setEnderecos(e1);
		
		e1.setUsuarios(cli1);

	
		marcaRepository.saveAll(Arrays.asList(mac1, mac2));
		modeloRepository.saveAll(Arrays.asList(m1, m2));
		//tipoCarroRepository.saveAll(Arrays.asList(t1));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
}
