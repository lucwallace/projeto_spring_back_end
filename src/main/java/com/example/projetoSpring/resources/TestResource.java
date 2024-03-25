package com.example.projetoSpring.resources;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoSpring.domain.ERole;
import com.example.projetoSpring.domain.Role;
import com.example.projetoSpring.domain.Usuario;
import com.example.projetoSpring.payload.request.LoginRequest;
import com.example.projetoSpring.payload.request.SignupRequest;
import com.example.projetoSpring.payload.response.JwtResponse;
import com.example.projetoSpring.payload.response.MessageResponse;
import com.example.projetoSpring.repositories.RoleRepository;
import com.example.projetoSpring.repositories.UsuarioRepository;
import com.example.projetoSpring.security.jwt.JwtUtils;
import com.example.projetoSpring.service.UsuarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestResource {

	@GetMapping("/all")
	public String allAcess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAcess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String modAcess() {
		return "Moderator Content.";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAcess() {
		return "Admin Content.";
	}
	
}
