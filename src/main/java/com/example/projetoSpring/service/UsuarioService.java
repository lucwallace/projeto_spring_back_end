package com.example.projetoSpring.service;

import com.example.projetoSpring.domain.ERole;
import com.example.projetoSpring.domain.Role;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.dto.LoginRequestDTO;
import com.example.projetoSpring.dto.RegistroUsuarioDTO;
import com.example.projetoSpring.payload.request.LoginRequest;
import com.example.projetoSpring.repositories.RoleRepository;
import com.example.projetoSpring.repositories.UsuarioRepository;
import com.example.projetoSpring.resources.UsuarioResource;
import com.example.projetoSpring.utils.JwtUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.context.annotation.Lazy;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UsuarioService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UsuarioService.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    // Injetando o AuthenticationManager com @Lazy para adiar sua resolução
    @Autowired
    public UsuarioService(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("### loadUserByUsername ###");

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
    }

    public String loginUsuario(LoginRequestDTO loginRequestDTO) {
        String token = "";
        logger.info("### Iniciando as validações do login ###");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );
            token = jwtUtil.generateToken(authentication.getName(), authentication.getAuthorities());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário e senha incorretos");
        } catch (Exception e) {
            logger.error("### Error no loginUsuario ###");
            logger.error("### " + e.getMessage() + " ###");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro inesperado ao autenticar usuário");
        }
        return token;
    }

    public Usuario registroUsuario(RegistroUsuarioDTO registroUsuarioDTO) {
        logger.info("### Iniciando as validações do cadastro de usuário ###");

        Usuario usuario = new Usuario();

        if (registroUsuarioDTO.getUserName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username não está preenchido.");
        }
        if (registroUsuarioDTO.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha não está preenchida.");
        }
        if (registroUsuarioDTO.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail não está preenchido.");
        }
        if (usuarioRepository.existsByUsername(registroUsuarioDTO.getUserName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username já existe.");
        }
        if (usuarioRepository.existsByEmail(registroUsuarioDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já existe.");
        }

        usuario.setUsername(registroUsuarioDTO.getUserName());
        usuario.setPassword(passwordEncoder.encode(registroUsuarioDTO.getPassword()));

        Role role = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ROLE " + ERole.USER + " não encontrada."));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        usuario.setRoles(roles);

        usuario.setEmail(registroUsuarioDTO.getEmail());
        usuario.setDataCriacao(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }
}
