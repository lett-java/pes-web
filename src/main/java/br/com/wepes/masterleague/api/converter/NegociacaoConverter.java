package br.com.wepes.masterleague.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wepes.masterleague.api.model.NegociacaoDTO;
import br.com.wepes.masterleague.api.model.cadastro.NegociacaoCadastroDTO;
import br.com.wepes.masterleague.domain.Negociacao;

@Component
public class NegociacaoConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Negociacao paraNegociacao(NegociacaoCadastroDTO negociacaoCadastroDTO) {
		return modelMapper.map(negociacaoCadastroDTO, Negociacao.class);
	}

	public NegociacaoDTO paraNegociacaoDTO(Negociacao negociacao) {
		return modelMapper.map(negociacao, NegociacaoDTO.class);
	}

	public List<NegociacaoDTO> paraListaNegociacaoDTOs(List<Negociacao> listaNegociacaos) {
		List<NegociacaoDTO> listaNegociacaoDTOs = new ArrayList<>();
		listaNegociacaos.forEach(negociacao -> listaNegociacaoDTOs.add(paraNegociacaoDTO(negociacao)));

		return listaNegociacaoDTOs;
	}

}
