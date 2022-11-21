package com.example.projetoSpring.enums;

import java.util.Iterator;

public enum TipoModeloEnum {

	HATCH(1, "HATCH"),
	SEDAN(2, "SEDAN"),
	CAMIONETE(3, "CAMIONETE"),
	SUV(4, "SUV"),
	ESPORTIVO(4, "ESPORTIVO"),
	CLASSICO(5, "CLÁSSICO"),
	MOTO(6, "MOTO"),
	BARCO(7, "BARCO"),
	RURAL(8, "RURAL"),
	AVIAO(9, "AVIÃO");
	
	
	
	private int codigo;
	private String descricao;
	
	private TipoModeloEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static TipoModeloEnum toEnum(Integer codigo) {

		if (codigo == null) {
			return null;
		}

		for (TipoModeloEnum x : TipoModeloEnum.values()) {
			if (codigo.equals(x.getCodigo())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
	
	
}
