package br.com.wepes.masterleague.api.model.view;

import java.math.BigDecimal;
import java.util.List;

import br.com.wepes.masterleague.domain.Assistencia;
import br.com.wepes.masterleague.domain.Gol;
import lombok.Data;

@Data
public class PartidaViewDTO {

	private Long id;
	private String clubeMandante;
	private String clubeVisitante;
	private List<Gol> gols;
	private List<Assistencia> assistencias;
	private Integer publico;
	private BigDecimal renda;
}
