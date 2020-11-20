package br.com.wepes.masterleague.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.wepes.masterleague.api.converter.ClubeConverter;
import br.com.wepes.masterleague.api.model.ClubeDTO;
import br.com.wepes.masterleague.api.model.atualizar.ClubeAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.exceptions.ClubeNaoEncontradoException;
import br.com.wepes.masterleague.services.ClubeService;

@RestController
@RequestMapping(value = "/clubes")
public class ClubeController {

	@Autowired
	private ClubeService clubeService;

	@Autowired
	private ClubeConverter clubeConverter;

	@GetMapping("/{idClube}")
	public ClubeDTO buscar(@PathVariable Long idClube) {
		Clube clube = clubeService.buscarPorId(idClube);

		return clubeConverter.paraClubeDTO(clube);
	}

	@GetMapping
	public List<ClubeDTO> buscar() {
		List<Clube> listaClubes = clubeService.buscar();

		return clubeConverter.paraListaClubeDTOs(listaClubes);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClubeDTO adicionar(@RequestBody ClubeCadastroDTO clubeCadastroDTO)
			throws ClubeNaoEncontradoException {
		Clube clube = clubeService.salvar(clubeCadastroDTO);

		return clubeConverter.paraClubeDTO(clube);
	}

	@PutMapping("/{idClube}")
	public ClubeDTO atualizar(@PathVariable Long idClube,
			@Valid @RequestBody ClubeAtualizarDTO clubeCadastroDTO) {
		Clube clube = clubeService.atualizar(clubeCadastroDTO, idClube);

		return clubeConverter.paraClubeDTO(clube);
	}

	@DeleteMapping("/{idClube}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idClube) {
		clubeService.remover(idClube);
	}

}
