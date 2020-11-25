package br.com.wepes.masterleague.api.model.cadastro;

import javax.validation.constraints.NotNull;

import br.com.wepes.masterleague.domain.enums.TipoTransferenciaEnum;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class NegociacaoCadastroDTO {

	@NotNull
	@ApiParam(required = true)
	private Long idJogador;

	@NotNull
	@ApiParam(required = true)
	private TipoTransferenciaEnum tipo;

	private Long idClubeOrigem;
	private Long idClubeDestino;

}
