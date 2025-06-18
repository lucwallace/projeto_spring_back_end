package com.example.projetoSpring.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.domain.Versao;
import com.example.projetoSpring.dto.ModeloRequestDTO;
import com.example.projetoSpring.dto.TipoModeloRequestDTO;
import com.example.projetoSpring.mapper.ModeloMapper;
import com.example.projetoSpring.records.ModeloResponseDTO;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.repositories.TipoModeloRepository;
import com.example.projetoSpring.repositories.VersaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.dto.ModeloDto;
import com.example.projetoSpring.enums.TipoModeloEnum;
import com.example.projetoSpring.repositories.ModeloRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository re;

    @Autowired
    private MarcaRepository reMarca;

    @Autowired
    private VersaoRepository reVersao;

    @Autowired
    private TipoModeloRepository reTipoModelo;

    public ModeloResponseDTO find(Integer id) {
        Modelo obj = re.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));

        return ModeloMapper.toDto(obj);
    }

    public Modelo insert(ModeloRequestDTO obj) {
        //obj.setId(null);

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo não preenchido.");
        } else if (obj.getIdMarca() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca não preenchido.");
        } else if (obj.getNomeVersao() == null || obj.getNomeVersao().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Versão não preenchido.");
        } else if (obj.getIdsTipoModelo() == null || obj.getIdsTipoModelo().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo modelo não preenchido.");
        }

        Optional<Modelo> objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        Modelo modelo = fromDTO(obj);

        if (objComp.isEmpty()) {
            String nomeModelo = modelo.getNome().toUpperCase().trim();
            modelo.setNome(nomeModelo);
            modelo.setDataCriacao(Timestamp.from(Instant.now()));

            List<TipoModelo> tipoModelos = obj.getIdsTipoModelo().stream()
                    .map(id -> reTipoModelo.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .filter(tipo -> {
                        Optional<Modelo> existente = re.findByModeloAndTipoModeloByNome(
                                obj.getNome().toUpperCase().trim(), tipo.getNome().toUpperCase().trim());
                        return existente.isEmpty();
                    })
                    .toList();

            modelo.setTipoModelos(tipoModelos);

            Modelo modeloFinal = modelo;

            tipoModelos.forEach(tipo -> tipo.getModelosTipo().add(modeloFinal));

            modelo = re.save(modelo);

            List<Versao> versoes = criarVersoes(obj.getNomeVersao(), modelo);

            if (!versoes.isEmpty()){
                reVersao.saveAll(versoes);
            }

            return modelo;
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Modelo do veiculo '" + obj.getNome() + "' já existe.");

        }
    }

    public Modelo fromDTO(ModeloRequestDTO objDto) {

        Modelo modelo = new Modelo();
        modelo.setId(objDto.getId());
        modelo.setNome(objDto.getNome());

        if (objDto.getId() != null) {
            Optional<Modelo> modeloData = re.findById(objDto.getId());
            modeloData.ifPresent(existing ->
                    modelo.setDataCriacao(existing.getDataCriacao())
            );
        }

        Marca marca = reMarca.findById(objDto.getIdMarca())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada"));

        re.findByModeloAndMarcaByNome(modelo.getNome().toUpperCase().trim(), marca.getNome().toUpperCase().trim())
                .ifPresent(m -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Modelo '" + modelo.getNome() + "' já está vinculado à marca '" + marca.getNome() + "'.");
                });

        modelo.setMarca(marca);

        return modelo;
    }

    public Modelo fromDTOUpdate(ModeloRequestDTO objDto) {

        Modelo modelo = re.findById(objDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));
        modelo.setNome(objDto.getNome());

        if (objDto.getId() != null) {
            Optional<Modelo> modeloData = re.findById(objDto.getId());
            modeloData.ifPresent(existing ->
                    modelo.setDataCriacao(existing.getDataCriacao())
            );
        }

        Marca marca = reMarca.findById(objDto.getIdMarca())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada"));

        re.findByModeloAndMarcaByNome(modelo.getNome().toUpperCase().trim(), marca.getNome().toUpperCase().trim())
                .ifPresent(m -> {
                    if (!m.getNome().trim().equalsIgnoreCase(objDto.getNome().trim())){
                        throw new ResponseStatusException(HttpStatus.CONFLICT,
                                "Modelo '" + m.getNome() + "' já está vinculado à marca '" + marca.getNome() + "'.");
                    }
                });

        modelo.setMarca(marca);

        return modelo;
    }

    public Modelo update(ModeloRequestDTO obj) {

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modelo não preenchido.");
        } else if (obj.getIdMarca() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Marca não preenchido.");
        } else if (obj.getNomeVersao() == null || obj.getNomeVersao().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Versão não preenchido.");
        } else if (obj.getIdsTipoModelo() == null || obj.getIdsTipoModelo().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo modelo não preenchido.");
        }

        Optional<Modelo> objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        if (objComp.isPresent() && !Objects.equals(obj.getId(), objComp.get().getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Modelo do veiculo '" + obj.getNome() + "' já existe.");
        }

        Modelo modelo = fromDTOUpdate(obj);

        if (modelo != null) {
            String nomeModelo = modelo.getNome().toUpperCase().trim();
            modelo.setNome(nomeModelo);
            modelo.setDataAlteracao(Timestamp.from(Instant.now()));

            List<Versao> novasVersoes = alterarVersoes(obj.getNomeVersao(), modelo);

            List<Versao> versoesAntigas = new ArrayList<>(modelo.getVersoes());

            versoesAntigas.stream()
                    .filter(antiga -> novasVersoes.stream().noneMatch(nova -> Objects.equals(nova.getId(), antiga.getId())))
                    .forEach(v -> {
                        v.setModelo(null);
                        reVersao.delete(v);
                    });

            modelo.getVersoes().clear();
            modelo.getVersoes().addAll(novasVersoes);

            Modelo finalModelo = modelo;

            List<TipoModelo> tipoModelos = obj.getIdsTipoModelo().stream()
                    .distinct()
                    .map(id -> reTipoModelo.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (tipoModelos.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Tipo Modelo encontrado com os dados fornecidos.");
            }

            modelo.getTipoModelos().forEach(tipo -> tipo.getModelosTipo().removeIf(m -> m.getId().equals(finalModelo.getId())));

            modelo.setTipoModelos(tipoModelos);

            Modelo finalTipoModelo = modelo;

            tipoModelos.forEach(tipo -> {
                if (tipo.getModelosTipo().stream().noneMatch(m -> m.getId().equals(finalTipoModelo.getId()))) {
                    tipo.getModelosTipo().add(finalTipoModelo);
                }
            });

            modelo = re.save(modelo);

            return modelo;

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não existe");
        }

    }

    public void delete(Integer id) {
        try {
            Modelo modelo = re.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Modelo não encontrado"));

            for (TipoModelo tipo : new ArrayList<>(modelo.getTipoModelos())) {
                tipo.getModelosTipo().remove(modelo);
            }

            modelo.getTipoModelos().clear();
            re.save(modelo);

            re.delete(modelo);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir modelo.");
        }
    }

    public List<Modelo> findAll() {
        return re.findAll();
    }

    public Page<Modelo> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return re.findAll(pageRequest);
    }

    private List<Versao> criarVersoes(String nomes, Modelo modelo) {
        return Arrays.stream(nomes.split(","))
                .map(String::trim)
                .map(nome -> {
                    Versao v = new Versao();
                    v.setNome(nome.trim().toUpperCase());
                    v.setModelo(modelo);
                    return v;
                })
                .filter(versao -> {
                    Optional<Versao> existente = reVersao.findVersaoAndModeloByNome(
                            versao.getNome().toUpperCase().trim(), modelo.getNome().toUpperCase().trim(), modelo.getMarca().getId());
                    return existente.isEmpty();
                }).toList();
    }

    private List<Versao> alterarVersoes(String nomes, Modelo modelo) {
        return Arrays.stream(nomes.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .distinct()
                .map(nome -> {
                    Optional<Versao> existente = reVersao.findVersaoAndModeloByNome(
                            nome,
                            modelo.getNome().toUpperCase().trim(),
                            modelo.getMarca().getId()
                    );

                    Versao v = new Versao();
                    if (existente.isPresent()) {
                        Versao existenteVersao = existente.get();
                        v.setId(existenteVersao.getId());
                        v.setNome(existenteVersao.getNome());
                    } else {
                        v.setNome(nome);
                    }

                    v.setModelo(modelo);
                    return v;
                })
                .collect(Collectors.toList());
    }

    private List<TipoModelo> criarlistTiposModelos(List<Integer> numeros) {
        return numeros.stream()
                .map(numero -> {
                    TipoModelo t = new TipoModelo();
                    t.setId(numero);
                    return t;
                })
                .toList();
    }
}