package br.com.wepes.masterleague.domain.enums;

import lombok.Getter;

@Getter
public enum PosicaoEnum {
	GOLEIRO("Goleiro"),
	LATERAL_DIREITO("Lateral Direito"),
	LATERAL_ESQUERDO("Lateral Esquerdo"),
	VOLANTE("Volante"),
	MEIO_CAMPO("Meio campo"),
	ATACANTE("Atacante");
	
	private String descricao;
	
	PosicaoEnum(String descricao) {
		this.descricao = descricao;
	}
}
