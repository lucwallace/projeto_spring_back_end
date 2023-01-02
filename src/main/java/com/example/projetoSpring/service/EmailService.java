package com.example.projetoSpring.service;

import org.springframework.mail.SimpleMailMessage;

import com.example.projetoSpring.domain.Usuario;

public interface EmailService {

	void sendOrderConfirmationEmail(Usuario obj);
	
	void sendEmail(SimpleMailMessage msg);
	
}
