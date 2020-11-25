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

import br.com.wepes.masterleague.api.converter.JogadorConverter;
import br.com.wepes.masterleague.api.model.JogadorDTO;
import br.com.wepes.masterleague.api.model.atualizar.JogadorAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.JogadorCadastroDTO;
import br.com.wepes.masterleague.domain.Jogador;
import br.com.wepes.masterleague.domain.exceptions.JogadorNaoEncontradoException;
import br.com.wepes.masterleague.services.JogadorService;

@RestController
@RequestMapping(value = "/jogadores")
public class JogadorController {

	@Autowired
	private JogadorService jogadorService;

	@Autowired
	private JogadorConverter jogadorConverter;

	@GetMapping("/{idJogador}")
	public JogadorDTO buscar(@PathVariable Long idJogador) {
		Jogador jogador = jogadorService.buscarPorId(idJogador);

		return jogadorConverter.paraJogadorDTO(jogador);
	}

	@GetMapping
	public List<JogadorDTO> buscar() {
		List<Jogador> listaJogadors = jogadorService.buscar();

		return jogadorConverter.paraListaJogadorDTOs(listaJogadors);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public JogadorDTO adicionar(@RequestBody JogadorCadastroDTO jogadorCadastroDTO)
			throws JogadorNaoEncontradoException {
		Jogador jogador = jogadorService.salvar(jogadorCadastroDTO);

		return jogadorConverter.paraJogadorDTO(jogador);
	}

	@PutMapping("/{idJogador}")
	public JogadorDTO atualizar(@PathVariable Long idJogador,
			@Valid @RequestBody JogadorAtualizarDTO jogadorCadastroDTO) {
		Jogador jogador = jogadorService.atualizar(jogadorCadastroDTO, idJogador);

		return jogadorConverter.paraJogadorDTO(jogador);
	}

	@DeleteMapping("/{idJogador}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idJogador) {
		jogadorService.remover(idJogador);
	}

}
