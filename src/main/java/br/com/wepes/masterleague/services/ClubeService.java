package br.com.wepes.masterleague.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.wepes.masterleague.api.converter.ClubeConverter;
import br.com.wepes.masterleague.api.model.atualizar.ClubeAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.repositories.ClubeRepository;
import br.com.wepes.masterleague.services.impl.ClubeServiceImpl;

@Service
public class ClubeService implements ClubeServiceImpl {

	private static final String NOME_CLUBE_JA_CADASTRADO = "Clube de nome %s, já existe nos cadastros";

	private static final String ID_CLUBE_NAO_ENCONTRADA = "Clube de id %d, não foi encontrado";

	@Autowired
	private ClubeRepository clubeRepository;

	@Autowired
	private ClubeConverter clubeConverter;

	@Override
	public Clube salvar(ClubeCadastroDTO clubeCadastroDTO) {
		validarCadastro(clubeCadastroDTO);
		return clubeRepository.save(clubeConverter.paraClube(clubeCadastroDTO));
	}

	@Override
	public Clube buscarPorId(Long idClube) {
		return clubeRepository.findById(idClube).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(ID_CLUBE_NAO_ENCONTRADA, idClube)));
	}

	@Override
	public void remover(Long idClube) {
		try {
			clubeRepository.deleteById(idClube);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(ID_CLUBE_NAO_ENCONTRADA, idClube));
		} 
	}

	@Override
	public Clube atualizar(@Valid ClubeAtualizarDTO clubeAtualizarDTO, Long idClube) {
		Clube clubeAtualizar = buscarPorId(idClube);

		validarAtualizacao(clubeAtualizarDTO, clubeAtualizar);
		clubeAtualizar = clubeConverter.paraClube(clubeAtualizarDTO);
		clubeAtualizar.setId(idClube);

		return clubeRepository.save(clubeAtualizar);
	}

	private void validarAtualizacao(ClubeAtualizarDTO clubeAtualizarDTO, Clube clubeAtualizar) {
		Optional<Clube> clubeOptional = clubeRepository.findByNomeIgnoreCase(clubeAtualizarDTO.getNome());
		if (clubeOptional.isPresent() && !clubeOptional.get().getId().equals(clubeAtualizar.getId())) {
			throw new NegocioException(String.format(NOME_CLUBE_JA_CADASTRADO, clubeAtualizarDTO.getNome()));
		}
	}

	private void validarCadastro(ClubeCadastroDTO clubeCadastroDTO) {
		Optional<Clube> clubeOptional = clubeRepository.findByNomeIgnoreCase(clubeCadastroDTO.getNome());
		if (clubeOptional.isPresent() || clubeCadastroDTO.getNome().isEmpty()) {
			throw new NegocioException(String.format(NOME_CLUBE_JA_CADASTRADO, clubeCadastroDTO.getNome()));
		}
	}

	@Override
	public List<Clube> buscar() {
		return clubeRepository.findAll();
	}

	public Clube findByNomeIgnoreCase(String nomeClube) {
		return clubeRepository.findByNomeIgnoreCase(nomeClube).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format("Clube %s, não encontrado!", nomeClube)));
	}
}
