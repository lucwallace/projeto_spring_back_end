package com.example.projetoSpring.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;




@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PUBLIC = { "/marcas/findById/**", "/marcas/findAll", "/tiposModelos/findById/**", "/tiposModelos/findAll",
	"/modelos/findById/**", "/modelos/findAll"};
	private static final String[] AUTHENTICATED = { "" };
	private static final String[] AUTHENTICATED_ADMIN = { "/marcas/criarMarca", "/marcas/modificarMarca/**", "/marcas/deletaMarca/**",
	"/tiposModelos/criarTipoModelo", "/tiposModelos/modificarTipoModelo/**", "/tiposModelos/deleteTipoModelo/**",
	"/modelos/criarModelo", "/modelos/modificarModelo/**", "/modelos/deletaModelo/**"};

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
						.authenticationEntryPoint(customAuthenticationEntryPoint())
				)
				.exceptionHandling(ex -> ex
						.accessDeniedHandler(customAccessDeniedHandler())
				);

		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		grantedAuthoritiesConverter.setAuthoritiesClaimName("my_custom_roles");
		grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

	@Bean
	public AuthenticationEntryPoint customAuthenticationEntryPoint() {
		return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) -> {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			response.getWriter().write("""
                {
                    "error": "Unauthorized",
                    "message": "Você não está autenticado. Faça login para acessar este recurso."
                }
            """);
		};
	}

	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return (HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) -> {
			response.setStatus(HttpStatus.FORBIDDEN.value());
			response.setContentType("application/json");
			response.getWriter().write("""
                {
                    "error": "Forbidden",
                    "message": "Você não tem permissão para acessar este recurso."
                }
            """);
		};
	}
}