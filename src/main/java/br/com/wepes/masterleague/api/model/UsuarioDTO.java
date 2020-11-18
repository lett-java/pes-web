package br.com.wepes.masterleague.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UsuarioDTO {

	private Long id;
	private String nome;

}
