package br.com.wepes.masterleague.api.model.atualizar;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.enums.PosicaoEnum;
import lombok.Data;

@Data
public class JogadorAtualizarDTO {

	@NotBlank
	private String nome;
	
	@NotNull
	private Integer overall;
	
	@NotNull
	private Integer idade;
	
	@NotNull
	private PosicaoEnum posicao;
	
	private Integer contrato;
	private BigDecimal salario = BigDecimal.ZERO;
	private Clube clube;

}
