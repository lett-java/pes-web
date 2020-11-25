package br.com.wepes.masterleague.api.model;

import java.math.BigDecimal;

import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.Jogador;
import br.com.wepes.masterleague.domain.enums.TipoTransferenciaEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NegociacaoDTO {

	@EqualsAndHashCode.Include
	private Long id;
	
	private BigDecimal valorTransacionado = BigDecimal.ZERO;
	private Jogador jogador;
	private Clube clubeOrigem;
	private Clube clubeDestino;
	private TipoTransferenciaEnum tipo;

}
