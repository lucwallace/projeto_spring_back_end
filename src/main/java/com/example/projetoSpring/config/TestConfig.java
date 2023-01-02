package com.example.projetoSpring.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.projetoSpring.service.DBService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	public DBService se;
	
	@Bean
	public boolean instantiateTestDatabase() throws ParseException{
		se.instantiateTestDatabase();
		return true;
	}

}
