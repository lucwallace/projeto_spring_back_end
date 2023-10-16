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
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
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
			"/tipoCarros/**"
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
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		http.authorizeHttpRequests().antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll().antMatchers(HttpMethod.PUT, PUBLIC_MATCHERS_PUT).permitAll().anyRequest().authenticated();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		
	} 
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

}
