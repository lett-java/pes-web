package br.com.wepes.masterleague.domain.enums;

import lombok.Getter;

@Getter
public enum TipoTransferenciaEnum {
	
	TRANSFERENCIA("Transferência"),
	EMPRESTIMO("Emprestimo"),
	EMPRESTIMO_OPCAO_COMPRA("Emprestimo com opção de compra");
	
	private String descricao;
	
	TipoTransferenciaEnum(String descricao) {
		this.descricao = descricao;
	}

}
