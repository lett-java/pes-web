package br.com.wepes.masterleague.services.impl;

import java.util.List;

import br.com.wepes.masterleague.api.model.cadastro.NegociacaoCadastroDTO;
import br.com.wepes.masterleague.api.model.view.NegociacaoViewDTO;

public interface NegociacaoServiceImpl {

	NegociacaoViewDTO salvar(NegociacaoCadastroDTO negociacaoCadastroDTO);

	List<NegociacaoViewDTO> buscar();

	NegociacaoViewDTO buscarPorId(Long idNegociacao);
}
