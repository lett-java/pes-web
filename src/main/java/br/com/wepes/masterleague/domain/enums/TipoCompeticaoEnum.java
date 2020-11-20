package br.com.wepes.masterleague.domain.enums;

import lombok.Getter;

@Getter
public enum TipoCompeticaoEnum {
	
	REGIONAL(1, "Regional"),
	LIGA(2, "Liga"),
	CONTINENTAL(3, "Continental"),
	COPA_DA_LIGA(4, "Copa da Liga"),
	MUNDIAL(5, "Mundial");
	
	private int cod;
	private String descricao;
	
	TipoCompeticaoEnum(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public static TipoCompeticaoEnum toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		for (TipoCompeticaoEnum x : TipoCompeticaoEnum.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		throw new IllegalArgumentException("Id inválido: " + cod);
	}
	

}
