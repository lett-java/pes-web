package br.com.wepes.masterleague.api.converter;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.wepes.masterleague.api.model.ClubeDTO;
import br.com.wepes.masterleague.api.model.atualizar.ClubeAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.domain.Clube;

@Component
public class ClubeConverter {

	@Autowired
	private ModelMapper modelMapper;

	public Clube paraClube(ClubeCadastroDTO clubeCadastroDTO) {
		return modelMapper.map(clubeCadastroDTO, Clube.class);
	}

	public ClubeDTO paraClubeDTO(Clube clube) {
		return modelMapper.map(clube, ClubeDTO.class);
	}

	public Clube paraClube(ClubeAtualizarDTO clubeAtualizarDTO) {
		return modelMapper.map(clubeAtualizarDTO, Clube.class);
	}

	public List<ClubeDTO> paraListaClubeDTOs(List<Clube> listaClubes) {
		List<ClubeDTO> listaClubeDTOs = new ArrayList<>();
		listaClubes.forEach(clube -> listaClubeDTOs.add(paraClubeDTO(clube)));

		return listaClubeDTOs;
	}

}
