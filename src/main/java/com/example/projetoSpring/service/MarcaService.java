package com.example.projetoSpring.service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Modelo;
import com.example.projetoSpring.dto.MarcaDto;
import com.example.projetoSpring.repositories.MarcaRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository re;

    public Marca find(Integer id) {
        Optional<Marca> obj = re.findById(id);
        return obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca não encontrada"));
    }

    public Marca insert(MarcaDto obj) {
        //obj.setId(null);

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da marca não preenchido.");
        }

        Marca objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        Marca marca = fromDTO(obj);

        if (objComp == null) {
            String nomeMarca = marca.getNome().toUpperCase().trim();
            marca.setNome(nomeMarca);
            marca.setDataCriacao(Timestamp.from(Instant.now()));
            return re.save(marca);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A Marca do veiculo '" + obj.getNome() + "' já existe.");

        }
    }

    public Marca fromDTO(MarcaDto objDto) {

        Marca marca = new Marca();

        marca.setId(objDto.getId());
        marca.setNome(objDto.getNome());

        if (objDto.getId() != null) {
            Optional<Marca> marcaData = re.findById(objDto.getId());
            marcaData.ifPresent(existing ->
                    marca.setDataCriacao(existing.getDataCriacao())
            );
        }

        return marca;
    }

    public Marca update(MarcaDto obj) {

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da marca não preenchido.");
        }

        boolean existsByIdMarca = re.existsById(obj.getId());

        Marca objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        if (objComp != null && !Objects.equals(obj.getId(), objComp.getId()) && obj.getNome().equalsIgnoreCase(objComp.getNome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "A Marca do veiculo '" + obj.getNome() + "' já existe.");
        }

        Marca marca = fromDTO(obj);

        if (existsByIdMarca) {
            String nomeMarca = marca.getNome().toUpperCase().trim();
            marca.setNome(nomeMarca);
            marca.setDataAlteracao(Timestamp.from(Instant.now()));
            return re.save(marca);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca de veículo não existe");
        }

    }

    public void delete(Integer id) {
        try {
            boolean existsByIdMarca = re.existsById(id);

            if (existsByIdMarca) {
                re.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Marca de veículo não existe");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir marca.");
        }
    }

    public List<Marca> findAll() {
        return re.findAll();
    }

    public Page<Marca> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return re.findAll(pageRequest);
    }

}
