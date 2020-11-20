package br.com.wepes.masterleague.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.wepes.masterleague.domain.Jogador;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClubeDTO {

	@EqualsAndHashCode.Include
	private Long id;
	private String nome;
	private Integer vitoria = 0;
	private Integer empate = 0;
	private Integer derrota = 0;
	private Integer golPro = 0;
	private Integer golSofrido = 0;
	private BigDecimal caixa = BigDecimal.ZERO;
	private Long idTreinador;
	private List<Jogador> jogadores = new ArrayList<>();

}
