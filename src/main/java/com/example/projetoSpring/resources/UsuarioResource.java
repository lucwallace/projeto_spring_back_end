package com.example.projetoSpring.resources;

import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.LoginRequestDTO;
import com.example.projetoSpring.dto.RegistroUsuarioDTO;
import com.example.projetoSpring.dto.ReturnResponse;
import com.example.projetoSpring.dto.TokenResponseDTO;
import com.example.projetoSpring.service.UsuarioService;
import com.example.projetoSpring.utils.JwtUtil;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "public/usuarios")
public class UsuarioResource {
    private static final Logger logger = LogManager.getLogger(UsuarioResource.class);

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        logger.info("Efetuando a request de login");

        try {

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();

            tokenResponseDTO.setToken(service.loginUsuario(loginRequestDTO));

            return ResponseEntity.ok(tokenResponseDTO);

        } catch (Exception e) {
            logger.error("### Error na request de login ###");
            logger.error("### " + e.getMessage() + " ###");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao login do usuario.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ReturnResponse> register(@Valid @RequestBody RegistroUsuarioDTO registroUsuarioDTO) {

        logger.info("Efetuando a request de registro de usuário");

        Usuario usuarioReturn = service.registroUsuario(registroUsuarioDTO);

        ReturnResponse returnResponse = new ReturnResponse();

        if (usuarioReturn != null && usuarioReturn.getId() != null) {
            returnResponse.setMessaje("Cadastro de usuário efetuado com sucesso.");
            return ResponseEntity.status(HttpStatus.CREATED).body(returnResponse);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao fazer o cadastro de usuário.");
        }


    }
}
