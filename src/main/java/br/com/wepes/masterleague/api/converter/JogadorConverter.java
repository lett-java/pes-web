package br.com.wepes.masterleague.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wepes.masterleague.api.model.JogadorDTO;
import br.com.wepes.masterleague.api.model.atualizar.JogadorAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.JogadorCadastroDTO;
import br.com.wepes.masterleague.domain.Jogador;

@Component
public class JogadorConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Jogador paraJogador(JogadorCadastroDTO jogadorCadastroDTO) {
		return modelMapper.map(jogadorCadastroDTO, Jogador.class);
	}

	public JogadorDTO paraJogadorDTO(Jogador jogador) {
		return modelMapper.map(jogador, JogadorDTO.class);
	}

	public Jogador paraJogador(JogadorAtualizarDTO jogadorAtualizarDTO) {
		return modelMapper.map(jogadorAtualizarDTO, Jogador.class);
	}

	public List<JogadorDTO> paraListaJogadorDTOs(List<Jogador> listaJogadors) {
		List<JogadorDTO> listaJogadorDTOs = new ArrayList<>();
		listaJogadors.forEach(jogador -> listaJogadorDTOs.add(paraJogadorDTO(jogador)));

		return listaJogadorDTOs;
	}

	public JogadorAtualizarDTO paraJogadorAtualizarDTO(Jogador jogador) {
		return modelMapper.map(jogador, JogadorAtualizarDTO.class);
	}

}
