package com.example.projetoSpring.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
	
	@Autowired
	private Environment env;
	
	private static final String [] PUBLIC_MATCHERS = {
			"/h2-console/**",
			"/usuarios/**",
			"/marcas/**",
			"/modelos/**",
			"/cidades/**",
			"/estados/**",
			"/enderecos/**",
			"/tipoCarros/**",
			"/tipoUsuario/**",
	};
	
	private static final String [] PUBLIC_MATCHERS_GET = {
			"/usuarios/**",
			"/marcas/**",
			"/modelos/**",
			"/cidades/**",
			"/estados/**",
			"/enderecos/**",
			"/tipoCarros/**"
	};
	
	private static final String [] PUBLIC_MATCHERS_POST = {
			"/usuarios/**",
			"/marcas/**",
			"/modelos/**",
			"/cidades/**",
			"/estados/**",
			"/enderecos/**",
			"/tipoCarros/**"
	};
	
	private static final String [] PUBLIC_MATCHERS_PUT = {
			"/usuarios/**",
			"/marcas/**",
			"/modelos/**",
			"/cidades/**",
			"/estados/**",
			"/enderecos/**",
			"/tipoCarros/**"
	}; 

}
