package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.ImagemPerfil;
import com.example.projetoSpring.domain.Marca;
import com.example.projetoSpring.repositories.ImagemPerfilRepository;
import com.example.projetoSpring.service.exceptions.ObjectNotFoundException;
import com.example.projetoSpring.utils.ImagemUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;


@Service
public class ImagemPerfilService {
    @Autowired
    private ImagemPerfilRepository imagemPerfilRepository;

    public ImagemPerfil upload(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        ImagemPerfil image = new ImagemPerfil(fileName, file.getContentType(), file.getBytes());

        return imagemPerfilRepository.save(image);
    }

    public ImagemPerfil getImages(String id){
        return imagemPerfilRepository.findById(id).get();
    }

    public Stream<ImagemPerfil> getAllImages(){
        return imagemPerfilRepository.findAll().stream();
    }

}
