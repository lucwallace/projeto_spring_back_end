package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.*;
import com.example.projetoSpring.dto.AnuncioRequestDTO;
import com.example.projetoSpring.mapper.AnuncioMapper;
import com.example.projetoSpring.records.AnuncioResponseDTO;
import com.example.projetoSpring.repositories.AnuncianteRepository;
import com.example.projetoSpring.repositories.AnuncioRepository;
import com.example.projetoSpring.repositories.ModeloRepository;
import com.example.projetoSpring.repositories.OpcionaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnuncioService {

    @Autowired
    private AnuncioRepository re;

    @Autowired
    private AnuncianteRepository reAnunciante;

    @Autowired
    private ModeloRepository reModelo;

    @Autowired
    private OpcionaisRepository reOpcional;

    public AnuncioResponseDTO find(Integer id) {
        Anuncio obj = re.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anúncio não encontrado"));

        return AnuncioMapper.toDto(obj);
    }

    public AnuncioResponseDTO findByStatus(StatusAnuncio status) {
        Anuncio obj = re.findByStatus(status).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anúncio não encontrado"));

        return AnuncioMapper.toDto(obj);
    }

    public Anuncio insert(AnuncioRequestDTO obj) {
        if (obj.getPreco() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço não preenchido.");
        } else if (obj.getIdModelo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O modelo não preenchido.");
        } else if (obj.getIdsOpcionais() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opcional não preenchido.");
        } else if (obj.getIdTipoModelo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de modelo não preenchido.");
        } else if (obj.getIdVersao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A versão não preenchida.");
        }

        Anuncio anuncio = fromDTO(obj);

        anuncio = re.save(anuncio);

        return anuncio;

    }

    public Anuncio fromDTO(AnuncioRequestDTO dto) {
        Anuncio anuncio = new Anuncio();

        anuncio.setPreco(dto.getPreco());
        anuncio.setStatus(StatusAnuncio.EM_ANALISE);

        List<Opcionais> opcionais = dto.getIdsOpcionais().stream()
                .map(id -> reOpcional.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe o opcional com ID: " + id)))
                .toList();

        for (Opcionais o : opcionais) {
            o.getAnuncioOpcional().add(anuncio);
        }

        anuncio.setOpcionalAnuncio(opcionais);

        Optional<Modelo> modelo = reModelo.findByModeloAndTipoModeloAndVersaoById(dto.getIdModelo(), dto.getIdTipoModelo(), dto.getIdVersao());

        if (modelo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado o modelo com os dados fornecidos");
        }

        TipoModelo tipoFiltrado = modelo.get().getTipoModelos().stream()
                .filter(t -> t.getId().equals(dto.getIdTipoModelo()))
                .findFirst()
                .orElse(null);

        Versao versaoFiltrada = modelo.get().getVersoes().stream()
                        .filter(v -> v.getId().equals(dto.getIdVersao()))
                        .findFirst()
                        .orElse(null);

        anuncio.setModelo(modelo.get());

        anuncio.setTipoModelos(tipoFiltrado);
        anuncio.setVersao(versaoFiltrada);
        anuncio.setDescricao(dto.getDescricao());

        anuncio.setDataCriacao(Timestamp.from(Instant.now()));

        Anunciante anunciante = getUsuarioInfoDoToken();

        if (anunciante != null) {
            anuncio.setAnunciante(anunciante);
        }

        return anuncio;
    }

    private Anunciante getUsuarioInfoDoToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt jwt) {
            String id = jwt.getSubject(); // o "sub", ID único do usuário
            String username = jwt.getClaim("preferred_username"); // nome de usuário

            Anunciante anunciante = reAnunciante.findAnuncianteByUUID(UUID.fromString(id));

            if (anunciante == null) {

                anunciante = new Anunciante();
                anunciante.setUuid_usuario(UUID.fromString(id));
                anunciante.setUsername(username);

                anunciante = reAnunciante.save(anunciante);

            }

            return anunciante;
        }
        return null;
    }

    public Anuncio update(AnuncioRequestDTO obj) {
        if (obj.getPreco() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O preço não preenchido.");
        } else if (obj.getIdModelo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O modelo não preenchido.");
        } else if (obj.getIdsOpcionais() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Opcional não preenchido.");
        } else if (obj.getIdTipoModelo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de modelo não preenchido.");
        } else if (obj.getIdVersao() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A versão não preenchida.");
        }

        if (re.existsById(obj.getId())) {

            Anuncio anuncio = fromDTOUpdate(obj);

            anuncio = re.save(anuncio);

            return anuncio;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe o anúncio.");
        }

    }

    public Anuncio fromDTOUpdate(AnuncioRequestDTO dto) {
        Anuncio anuncio = new Anuncio();

        Optional<Anuncio> a = re.findById(dto.getId());

        anuncio.setId(dto.getId());
        anuncio.setPreco(dto.getPreco());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean validaRole = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equalsIgnoreCase("ROLE_admin"));

        if (validaRole) {
            anuncio.setStatus(StatusAnuncio.valueOf(dto.getStatus()));
        } else {
            anuncio.setStatus(a.get().getStatus());
        }

        List<Opcionais> opcionais = dto.getIdsOpcionais().stream()
                .map(id -> reOpcional.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe o opcional com ID: " + id)))
                .toList();

        anuncio.setOpcionalAnuncio(opcionais);

        Optional<Modelo> modelo = reModelo.findByModeloAndTipoModeloAndVersaoById(dto.getIdModelo(), dto.getIdTipoModelo(), dto.getIdVersao());

        if (modelo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi encontrado o modelo com os dados fornecidos");
        }

        anuncio.setModelo(modelo.get());

        anuncio.setTipoModelos(modelo.get().getTipoModelos().get(0));
        anuncio.setVersao(modelo.get().getVersoes().get(0));

        anuncio.setDescricao(dto.getDescricao());

        anuncio.setDataAlteracao(Timestamp.from(Instant.now()));

        a.ifPresent(value -> anuncio.setDataCriacao(value.getDataCriacao()));

        Anunciante anunciante = getUsuarioInfoDoToken();

        if (anunciante != null) {
            anuncio.setAnunciante(anunciante);
        }

        return anuncio;
    }

    public void delete(Integer id) {
        try {
            Anuncio anuncio = re.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Anúncio não encontrado"));

            re.delete(anuncio);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel excluir o anúncio.");
        }
    }

    public List<Anuncio> findAll() {
        return re.findAll();
    }
}
