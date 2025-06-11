package com.example.projetoSpring.config;

import com.example.projetoSpring.security.jwt.AuthEntryPointJwt;
import com.example.projetoSpring.security.jwt.AuthTokenFilter;
import com.example.projetoSpring.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PUBLIC = { "/marcas/findById/**", "/marcas/findAll" };
	private static final String[] AUTHENTICATED = { "" };
	private static final String[] AUTHENTICATED_ADMIN = { "/marcas/criarMarca", "/marcas/modificarMarca/**", "/marcas/deletaMarca/**" };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // Desabilitado para REST APIs
				.authorizeHttpRequests(authz -> authz
						.requestMatchers(PUBLIC).permitAll()
						.requestMatchers(AUTHENTICATED_ADMIN).hasRole("admin")
						.requestMatchers(AUTHENTICATED).hasAnyRole("default-roles-master", "admin")
						.anyRequest().authenticated()
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				);
		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
		converter.setAuthoritiesClaimName("my_custom_roles");
		converter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
		jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
		return jwtConverter;
	}
}