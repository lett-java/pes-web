package br.com.wepes.masterleague.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UsuarioCadastroDTO {

	@NotBlank
	private String nome;
}
