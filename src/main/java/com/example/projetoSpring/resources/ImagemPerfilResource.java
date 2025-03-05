package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.ImagemPerfil;
import com.example.projetoSpring.domain.ImagemPerfilResponse;
import com.example.projetoSpring.message.ResponseMessage;
import com.example.projetoSpring.openApi.ImageUserOpenApiController;
import com.example.projetoSpring.service.ImagemPerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/imagemperfil")
public class ImagemPerfilResource implements ImageUserOpenApiController {

    @Autowired
    private ImagemPerfilService imagemPerfilService;

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id_user") Long idUser){
        String message = "";

        try {
            imagemPerfilService.upload(file, idUser);

            message = "Upload efetuado com sucesso";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));

        } catch (Exception e) {
            message = "Upload não efetuado";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }

        //return ResponseEntity.ok(new ResponseMessage(message));
    }

    @RequestMapping(method=RequestMethod.PUT)
    public ResponseEntity<ResponseMessage> uploadFileUpdate(@RequestParam("file") MultipartFile file, @RequestParam("id_user") Long idUser, @RequestParam("id_upload") Long idUpload){
        String message = "";

        try {
            imagemPerfilService.upload(file, idUser);

            message = "Update do Upload efetuado com sucesso";
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (Exception e) {
            message = "Update Upload não efetuado";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
        }

        //return ResponseEntity.ok(new ResponseMessage(message));
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<ImagemPerfilResponse>> getListFile(){
        List<ImagemPerfilResponse> files = imagemPerfilService.getAllImages().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/imagemperfil/")
                    .path(dbFile.getId())
                    .toUriString();

            return new ImagemPerfilResponse(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getImageData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getFiles(@PathVariable String id) {
        ImagemPerfil imagemPerfil = imagemPerfilService.getImages(id);

        return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagemPerfil.getName() + "\"")
               .body(imagemPerfil.getImageData());
    }

    @RequestMapping(value="/usuario/{id}", method=RequestMethod.GET)
    public ResponseEntity<byte[]> getFilesByUser(@PathVariable Long id) {
        ImagemPerfil imagemPerfil = imagemPerfilService.getImagesByIdUser(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imagemPerfil.getName() + "\"")
                .body(imagemPerfil.getImageData());
    }

}
