package com.example.projetoSpring.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.projetoSpring.domain.Anunciante;
import com.example.projetoSpring.domain.Anuncio;
import com.example.projetoSpring.domain.ImagemAnuncio;
import com.example.projetoSpring.repositories.AnuncioRepository;
import com.example.projetoSpring.repositories.ImagemAnuncianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImagemAnuncioService {

    @Autowired
    private ImagemAnuncianteRepository re;

    @Autowired
    private AnuncioRepository reAnuncio;

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.max_images}")
    private Integer maxImages;

    public List<ImagemAnuncio> upload(List<MultipartFile> files, Integer idAnuncio) throws IOException {
        List<ImagemAnuncio> imagensSalvas = new ArrayList<>();
        int contador = 1;

        if (files.size() > maxImages) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Máximo de " + maxImages + " imagens permitidas por anúncio.");
        }

        Anunciante a = getUsuarioInfoDoToken();
        Anuncio obj = reAnuncio.findById(idAnuncio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anúncio não encontrado"));

        deletarImagensAnuncio(obj.getId());

        for (MultipartFile file : files) {
            String nomeBase = "img_" + System.currentTimeMillis() + "_" + contador++;
            Map<String, Object> options = ObjectUtils.asMap(
                    "folder", a.getUsername() + "-folder",
                    "public_id", nomeBase,
                    "overwrite", true,
                    "resource_type", "image"
            );

            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

                ImagemAnuncio imagemAnuncio = new ImagemAnuncio();
                imagemAnuncio.setUrl(uploadResult.get("secure_url").toString());
                imagemAnuncio.setPublicId((String) uploadResult.get("public_id"));
                imagemAnuncio.setPasta(a.getUsername() + "-folder");
                imagemAnuncio.setAnuncio(obj);

                imagensSalvas.add(imagemAnuncio);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao fazer upload da imagem: " + file.getOriginalFilename(), e);
            }
        }

        return re.saveAll(imagensSalvas);
    }

    private Anunciante getUsuarioInfoDoToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof Jwt jwt) {
            String id = jwt.getSubject(); // o "sub", ID único do usuário
            String username = jwt.getClaim("preferred_username"); // nome de usuário

            Anunciante anunciante = new Anunciante();

            anunciante.setUuid_usuario(UUID.fromString(id));
            anunciante.setUsername(username);

            return anunciante;
        }
        return null;
    }

    public void deletarImagensAnuncio(Integer idAnuncio) {
        Anunciante anunciante = getUsuarioInfoDoToken();

        Anuncio obj = reAnuncio.findById(idAnuncio)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anúncio não encontrado"));

        List<ImagemAnuncio> imagens = obj.getImagemAnuncios();

        for (ImagemAnuncio i: imagens){
            try {
                cloudinary.uploader().destroy(i.getPublicId(), ObjectUtils.emptyMap());
            } catch (Exception e) {
                throw new RuntimeException("Erro ao deletar imagem: " + i.getPublicId(), e);
            }
        }

        obj.getImagemAnuncios().clear();
        reAnuncio.save(obj);

    }
}
