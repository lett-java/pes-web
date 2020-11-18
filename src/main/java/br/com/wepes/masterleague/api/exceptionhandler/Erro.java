package br.com.wepes.masterleague.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Erro")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Erro {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 5)
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos.", position = 20)
	private String detail;
	
	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 30)
	private List<Object> objects;
	
	@ApiModel("ObjetoErro")
	@Getter
	@Builder
	public static class Object {
		
		@ApiModelProperty(example = "cpf")
		private String name;
		
		@ApiModelProperty(example = "O cpf é obrigatório")
		private String userMessage;
		
	}
	
}