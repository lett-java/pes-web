package br.com.wepes.masterleague.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioAtualizarDTO {

	@NotBlank
	private String nome;

}
