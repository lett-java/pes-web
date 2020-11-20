package br.com.wepes.masterleague.services.impl;

import br.com.wepes.masterleague.api.model.cadastro.UsuarioCadastroDTO;
import br.com.wepes.masterleague.domain.Usuario;

public interface UsuarioServiceImpl {

	Usuario salvar(UsuarioCadastroDTO usuarioCadastroDTO);
}
