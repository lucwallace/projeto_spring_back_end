package com.example.projetoSpring.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.example.projetoSpring.domain.Usuario;

public abstract class AbstractEmail implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void sendOrderConfirmationEmail(Usuario obj) {
		SimpleMailMessage sw = prepareSimpleMessageFromUsuario(obj);
		sendEmail(sw);
	}
	
	protected SimpleMailMessage prepareSimpleMessageFromUsuario(Usuario obj) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(obj.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Contato sobre o carro");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
		
	}
	
}
