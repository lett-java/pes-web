package br.com.wepes.masterleague.api.model.cadastro;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.wepes.masterleague.domain.Assistencia;
import br.com.wepes.masterleague.domain.Gol;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class PartidaCadastroDTO {

	@NotNull
	@ApiParam(required = true)
	private Long idClubeMandante;

	@NotNull
	@ApiParam(required = true)
	private Long idClubeVisitante;

	private List<Gol> gols = new ArrayList<>();

	private List<Assistencia> assistencias = new ArrayList<>();
}