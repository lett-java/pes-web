package br.com.wepes.masterleague.services.impl;

import java.util.List;

import javax.validation.Valid;

import br.com.wepes.masterleague.api.model.atualizar.ClubeAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.domain.Clube;

public interface ClubeServiceImpl {

	Clube salvar(ClubeCadastroDTO clubeCadastroDTO);

	List<Clube> buscar();

	Clube atualizar(@Valid ClubeAtualizarDTO clubeAtualizarDTO, Long idClube);

	void remover(Long idClube);

	Clube buscarPorId(Long idClube);
}
