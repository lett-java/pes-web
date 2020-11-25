package br.com.wepes.masterleague.api.model.cadastro;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class ClubeCadastroDTO {

	@NotBlank
	@ApiParam(required = true)
	private String nome;

	private Long idTreinador;

}
