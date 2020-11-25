package br.com.wepes.masterleague.api.model.view;

import java.math.BigDecimal;

import br.com.wepes.masterleague.domain.enums.TipoTransferenciaEnum;
import lombok.Data;

@Data
public class NegociacaoViewDTO {
	
	private Long id;
	private String jogador;
	private String clubeOrigem;
	private String clubeDestino;
	private BigDecimal valorTransacionado;
	private TipoTransferenciaEnum tipo;

}
