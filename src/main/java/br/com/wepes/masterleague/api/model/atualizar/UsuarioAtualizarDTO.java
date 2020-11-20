package br.com.wepes.masterleague.api.model.atualizar;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioAtualizarDTO {

	@NotBlank
	private String nome;

}
