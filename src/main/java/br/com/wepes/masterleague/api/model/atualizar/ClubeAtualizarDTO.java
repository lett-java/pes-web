package br.com.wepes.masterleague.api.model.atualizar;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClubeAtualizarDTO {

	@NotBlank
	private String nome;

	private Long idTreinador;

}
