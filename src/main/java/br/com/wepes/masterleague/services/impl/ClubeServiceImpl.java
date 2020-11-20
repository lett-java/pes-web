package br.com.wepes.masterleague.services.impl;

import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.domain.Clube;

public interface ClubeServiceImpl {

	Clube salvar(ClubeCadastroDTO clubeCadastroDTO);
}
