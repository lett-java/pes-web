package br.com.wepes.masterleague.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wepes.masterleague.api.model.UsuarioAtualizarDTO;
import br.com.wepes.masterleague.api.model.UsuarioCadastroDTO;
import br.com.wepes.masterleague.api.model.UsuarioDTO;
import br.com.wepes.masterleague.domain.Usuario;

@Component
public class UsuarioConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario paraUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
		return modelMapper.map(usuarioCadastroDTO, Usuario.class);
	}

	public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}

	public Usuario paraUsuario(UsuarioAtualizarDTO usuarioAtualizarDTO) {
		return modelMapper.map(usuarioAtualizarDTO, Usuario.class);
	}

	public List<UsuarioDTO> paraListaUsuarioDTOs(List<Usuario> listaUsuarios) {
		List<UsuarioDTO> listaUsuarioDTOs = new ArrayList<>();
		listaUsuarios.forEach(usuario -> listaUsuarioDTOs.add(paraUsuarioDTO(usuario)));

		return listaUsuarioDTOs;
	}

}
