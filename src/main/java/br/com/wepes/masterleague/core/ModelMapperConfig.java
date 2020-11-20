package br.com.wepes.masterleague.core;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.wepes.masterleague.api.model.cadastro.UsuarioCadastroDTO;
import br.com.wepes.masterleague.domain.Usuario;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.createTypeMap(UsuarioCadastroDTO.class, Usuario.class)
				.addMappings(mapper -> mapper.skip(Usuario::setId));

		return modelMapper;
	}

}
