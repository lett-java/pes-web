package br.com.wepes.masterleague.services.impl;

import br.com.wepes.masterleague.api.model.UsuarioCadastroDTO;
import br.com.wepes.masterleague.domain.Usuario;

public interface UsuarioServiceImpl {

	Usuario salvar(UsuarioCadastroDTO usuarioCadastroDTO);
}
