package br.com.wepes.masterleague.services;

import java.util.List;

import br.com.wepes.masterleague.api.model.cadastro.PartidaCadastroDTO;
import br.com.wepes.masterleague.api.model.view.PartidaViewDTO;

public interface PartidaServiceImpl {

	PartidaViewDTO salvar(PartidaCadastroDTO partidaCadastroDTO);

	PartidaViewDTO buscarPorId(Long idPartida);

	void remover(Long idPartida);

	List<PartidaViewDTO> buscar();
}
