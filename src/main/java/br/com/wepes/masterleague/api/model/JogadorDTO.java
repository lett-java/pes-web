package br.com.wepes.masterleague.api.model;

import java.math.BigDecimal;

import br.com.wepes.masterleague.domain.enums.PosicaoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class JogadorDTO {

	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	private Integer idade = 0;
	private Integer overall = 0;
	private Integer contrato = 0;
	private BigDecimal valorDeMercado = BigDecimal.ZERO;
	private BigDecimal salario = BigDecimal.ZERO;
	private PosicaoEnum posicao;
	private ClubeDTO clube;

}
