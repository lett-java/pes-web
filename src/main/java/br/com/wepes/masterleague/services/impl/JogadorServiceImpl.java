package br.com.wepes.masterleague.services.impl;

import java.util.List;

import javax.validation.Valid;

import br.com.wepes.masterleague.api.model.atualizar.JogadorAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.JogadorCadastroDTO;
import br.com.wepes.masterleague.domain.Jogador;

public interface JogadorServiceImpl {

	Jogador salvar(JogadorCadastroDTO jogadorCadastroDTO);

	Jogador buscarPorId(Long idJogador);

	void remover(Long idJogador);

	Jogador atualizar(@Valid JogadorAtualizarDTO jogadorAtualizarDTO, Long idJogador);

	List<Jogador> buscar();

}
