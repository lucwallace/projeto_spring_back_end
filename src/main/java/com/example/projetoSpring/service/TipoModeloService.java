package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.TipoModelo;
import com.example.projetoSpring.dto.TipoModeloRequestDTO;
import com.example.projetoSpring.repositories.TipoModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoModeloService {

    @Autowired
    private TipoModeloRepository re;

    public TipoModelo find(Integer id) {
        Optional<TipoModelo> obj = re.findById(id);
        return obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo modelo não encontrado"));
    }

    public TipoModelo insert(TipoModeloRequestDTO obj) {
        //obj.setId(null);

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do tipo do modelo não preenchido.");
        }

        TipoModelo objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        TipoModelo tipoModelo = fromDTO(obj);

        if (objComp == null) {
            String nomeMarca = tipoModelo.getNome().toUpperCase().trim();
            tipoModelo.setNome(nomeMarca);
            tipoModelo.setDataCriacao(Timestamp.from(Instant.now()));
            return re.save(tipoModelo);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tipo do modelo do veiculo '" + obj.getNome() + "' já existe.");

        }
    }

    public TipoModelo fromDTO(TipoModeloRequestDTO objDto) {

        TipoModelo tipo = new TipoModelo();

        tipo.setId(objDto.getId());
        tipo.setNome(objDto.getNome());

        if (objDto.getId() != null) {
            Optional<TipoModelo> tipoData = re.findById(objDto.getId());
            tipoData.ifPresent(existing ->
                    tipo.setDataCriacao(existing.getDataCriacao())
            );
        }

        return tipo;
    }

    public TipoModelo update(TipoModeloRequestDTO obj) {

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do tipo do modelo não preenchido.");
        }

        boolean existsByIdTipoModelo = re.existsById(obj.getId());

        TipoModelo objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        if (objComp != null && !Objects.equals(obj.getId(), objComp.getId()) && obj.getNome().equalsIgnoreCase(objComp.getNome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Tipo do modelo do veiculo '" + obj.getNome() + "' já existe.");
        }

        TipoModelo tipoModelo = fromDTO(obj);

        if (existsByIdTipoModelo) {
            String nomeMarca = tipoModelo.getNome().toUpperCase().trim();
            tipoModelo.setNome(nomeMarca);
            tipoModelo.setDataAlteracao(Timestamp.from(Instant.now()));
            return re.save(tipoModelo);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo do modelo não existe");
        }

    }

    public void delete(Integer id) {
        try {
            boolean existsByIdTipoModelo = re.existsById(id);

            if (existsByIdTipoModelo) {
                re.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de modelo não existe");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir tipo do modelo.");
        }
    }

    public List<TipoModelo> findAll() {
        return re.findAll();
    }

}
