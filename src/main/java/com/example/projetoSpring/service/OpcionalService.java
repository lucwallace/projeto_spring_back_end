package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.Opcionais;
import com.example.projetoSpring.dto.OpcionalRequestDTO;
import com.example.projetoSpring.repositories.OpcionaisRepository;
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
public class OpcionalService {

    @Autowired
    private OpcionaisRepository re;

    public Opcionais find(Integer id) {
        Optional<Opcionais> obj = re.findById(id);
        return obj.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opcional não encontrado"));
    }

    public Opcionais insert(OpcionalRequestDTO obj) {
        //obj.setId(null);

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do opcional não preenchido.");
        }

        Opcionais objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        Opcionais opcionais = fromDTO(obj);

        if (objComp == null) {
            String nomeOpcional = opcionais.getNome().toUpperCase().trim();
            opcionais.setNome(nomeOpcional);
            opcionais.setDataCriacao(Timestamp.from(Instant.now()));
            return re.save(opcionais);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O opcional '" + obj.getNome() + "' já existe.");

        }
    }

    public Opcionais fromDTO(OpcionalRequestDTO objDto) {

        Opcionais opcionais = new Opcionais();

        opcionais.setId(objDto.getId());
        opcionais.setNome(objDto.getNome());

        if (objDto.getId() != null) {
            Optional<Opcionais> opcionalData = re.findById(objDto.getId());
            opcionalData.ifPresent(existing ->
                    opcionais.setDataCriacao(existing.getDataCriacao())
            );
        }

        return opcionais;
    }

    public Opcionais update(OpcionalRequestDTO obj) {

        if (obj.getNome() == null || obj.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do opcional não preenchido.");
        }

        boolean existsByIdOpcional = re.existsById(obj.getId());

        Opcionais objComp = re.findByNome(obj.getNome().toUpperCase().trim());

        if (objComp != null && !Objects.equals(obj.getId(), objComp.getId()) && obj.getNome().equalsIgnoreCase(objComp.getNome())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Opcional '" + obj.getNome() + "' já existe.");
        }

        Opcionais opcionais = fromDTO(obj);

        if (existsByIdOpcional) {
            String nomeOpcional = opcionais.getNome().toUpperCase().trim();
            opcionais.setNome(nomeOpcional);
            opcionais.setDataAlteracao(Timestamp.from(Instant.now()));
            return re.save(opcionais);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opcional não existe");
        }

    }

    public void delete(Integer id) {
        try {
            boolean existsByIdOpcional = re.existsById(id);

            if (existsByIdOpcional) {
                re.deleteById(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Opcional não existe");
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir o opcional.");
        }
    }

    public List<Opcionais> findAll() {
        return re.findAll();
    }

}
