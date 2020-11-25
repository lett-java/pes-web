package br.com.wepes.masterleague.api.model.cadastro;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.wepes.masterleague.domain.enums.PosicaoEnum;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class JogadorCadastroDTO {

	@NotBlank
	@ApiParam(required = true)
	private String nome;
	
	@NotNull
	@ApiParam(required = true)
	private Integer overall;
	
	@NotNull
	@ApiParam(required = true)
	private Integer idade;
	
	@NotNull
	@ApiParam(required = true)
	private PosicaoEnum posicao;
	
	public JogadorCadastroDTO(PosicaoEnum posicao, String nome, Integer idade, Integer overall) {
		this.posicao = posicao;
		this.nome = nome;
		this.idade = idade;
		this.overall = overall;
	}
	
	public JogadorCadastroDTO() {
	}
}
