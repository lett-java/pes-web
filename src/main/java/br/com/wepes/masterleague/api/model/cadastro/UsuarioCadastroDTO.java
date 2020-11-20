package br.com.wepes.masterleague.api.model.cadastro;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioCadastroDTO {

	@NotBlank
	private String nome;
}
