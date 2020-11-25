package br.com.wepes.masterleague.services.impl;

import java.util.List;

import javax.validation.Valid;

import br.com.wepes.masterleague.api.model.atualizar.UsuarioAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.UsuarioCadastroDTO;
import br.com.wepes.masterleague.domain.Usuario;

public interface UsuarioServiceImpl {

	Usuario salvar(UsuarioCadastroDTO usuarioCadastroDTO);

	Usuario buscarPorId(Long idUsuario);

	void remover(Long idUsuario);

	Usuario atualizar(@Valid UsuarioAtualizarDTO usuarioAtualizarDTO, Long idUsuario);

	List<Usuario> buscar();
}
