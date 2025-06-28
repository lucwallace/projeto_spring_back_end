package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.ImagemAnuncio;
import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.service.ImagemAnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/imagensAnuncio")
public class ImagensAnuncioResource {

    @Autowired
    private ImagemAnuncioService service;

    @PostMapping(value = "/upload")
    public ResponseEntity<ReturnResponse> uploadImagens(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("idAnuncio") Integer idAnuncio
    ) throws IOException {
        ReturnResponse returnResponse = new ReturnResponse();
        List<ImagemAnuncio> imagens = service.upload(files, idAnuncio);

        if (imagens != null) {
            returnResponse.setMessaje("Imagens cadastradas com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro das imagens.");
        }

    }

    @DeleteMapping(value = "/deleteImagensAnuncio/{id}")
    public ResponseEntity<ImagemAnuncio> delete(@PathVariable Integer id) {
        service.deletarImagensAnuncio(id);
        return ResponseEntity.noContent().build();
    }

}
