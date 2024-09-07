package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.ImagemPerfil;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.repositories.ImagemPerfilRepository;
import com.example.projetoSpring.repositories.UsuarioRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;
import com.example.projetoSpring.utils.ImagemUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class ImagemPerfilService {
    @Autowired
    private ImagemPerfilRepository imagemPerfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public ImagemPerfil upload(MultipartFile file, Long idUser) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Optional<Usuario> user = usuarioRepository.findById(idUser);

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não existe");
        }

        Usuario us = new Usuario();

        us.setId(user.get().getId());
        us.setEmail(user.get().getEmail());
        us.setUsername(user.get().getUsername());
        us.setPassword(user.get().getPassword());
        us.setRoles(user.get().getRoles());

        ImagemPerfil image = new ImagemPerfil(null, fileName, file.getContentType(), file.getBytes(), us);

        return imagemPerfilRepository.save(image);
    }

    public ImagemPerfil getImages(String id){
        return imagemPerfilRepository.findById(id).get();
    }

    public Stream<ImagemPerfil> getAllImages(){
        return imagemPerfilRepository.findAll().stream();
    }

}
