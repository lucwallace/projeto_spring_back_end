package com.example.projetoSpring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//Reference
//https://dzone.com/articles/spring-boot-profiles-1

@Configuration
@ConfigurationProperties("spring.datasource")
public class ProfilesConfig {
	
	private String driverClassName;
	private String url;
	private String userName;
	private String password;
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Profile("dev")
	@Bean
	public String devDatabaseConnection() {
		System.out.println("DB DE DEV CONECTADO");
		System.out.println(driverClassName);
		System.out.println(url);
		return "DB DE DEV CONECTADO";
	}
	
	@Profile("test")
	@Bean
	public String testDatabaseConnection() {
		System.out.println("DB DE TEST CONECTADO");
		System.out.println(driverClassName);
		System.out.println(url);
		return "DB DE TEST CONECTADO";
	}

}
