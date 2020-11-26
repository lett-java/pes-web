package br.com.wepes.masterleague.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.wepes.masterleague.api.model.cadastro.PartidaCadastroDTO;
import br.com.wepes.masterleague.api.model.view.PartidaViewDTO;
import br.com.wepes.masterleague.domain.exceptions.PartidaNaoEncontradaException;
import br.com.wepes.masterleague.services.PartidaService;

@RestController
@RequestMapping(value = "/partidas")
public class PartidaController {

	@Autowired
	private PartidaService partidaService;

	@GetMapping("/{idPartida}")
	public PartidaViewDTO buscar(@PathVariable Long idPartida) {
		return partidaService.buscarPorId(idPartida);
	}

	@GetMapping
	public List<PartidaViewDTO> buscar() {
		return partidaService.buscar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PartidaViewDTO adicionar(@RequestBody PartidaCadastroDTO partidaCadastroDTO)
			throws PartidaNaoEncontradaException {
		return partidaService.salvar(partidaCadastroDTO);

	}
}
