package com.example.projetoSpring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class ImagemPerfil {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nome;
    private String type;
    @Lob
    private byte[] imageData;

	public ImagemPerfil() {}

	public ImagemPerfil(String nome, String type, byte[] imageData) {
		this.nome = nome;
		this.type = type;
		this.imageData = imageData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
}
