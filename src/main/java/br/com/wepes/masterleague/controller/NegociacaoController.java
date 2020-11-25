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

import br.com.wepes.masterleague.api.model.cadastro.NegociacaoCadastroDTO;
import br.com.wepes.masterleague.api.model.view.NegociacaoViewDTO;
import br.com.wepes.masterleague.domain.exceptions.NegociacaoNaoEncontradoException;
import br.com.wepes.masterleague.services.NegociacaoService;

@RestController
@RequestMapping(value = "/negociacoes")
public class NegociacaoController {

	@Autowired
	private NegociacaoService negociacaoService;

	@GetMapping("/{idNegociacao}")
	public NegociacaoViewDTO buscar(@PathVariable Long idNegociacao) {
		return negociacaoService.buscarPorId(idNegociacao);
	}

	@GetMapping
	public List<NegociacaoViewDTO> buscar() {
		return negociacaoService.buscar();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NegociacaoViewDTO adicionar(@RequestBody NegociacaoCadastroDTO negociacaoCadastroDTO)
			throws NegociacaoNaoEncontradoException {
		return negociacaoService.salvar(negociacaoCadastroDTO);

	}
}
