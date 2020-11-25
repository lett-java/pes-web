package br.com.wepes.masterleague.domain.enums;

import lombok.Getter;

@Getter
public enum TipoCompeticaoEnum {
	
	REGIONAL("Regional"),
	LIGA("Liga"),
	CONTINENTAL("Continental"),
	COPA_DA_LIGA("Copa da Liga"),
	MUNDIAL("Mundial");
	
	private String descricao;
	
	TipoCompeticaoEnum(String descricao) {
		this.descricao = descricao;
	}
}
