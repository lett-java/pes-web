package br.com.wepes.masterleague.controller;

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

import br.com.wepes.masterleague.api.converter.UsuarioConverter;
import br.com.wepes.masterleague.api.model.UsuarioAtualizarDTO;
import br.com.wepes.masterleague.api.model.UsuarioCadastroDTO;
import br.com.wepes.masterleague.api.model.UsuarioDTO;
import br.com.wepes.masterleague.domain.Usuario;
import br.com.wepes.masterleague.domain.exceptions.UsuarioNaoEncontradoException;
import br.com.wepes.masterleague.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioConverter usuarioConverter;

	@GetMapping("/{idUsuario}")
	public UsuarioDTO buscar(@PathVariable Long idUsuario) {
		Usuario usuario = usuarioService.buscar(idUsuario);
		return usuarioConverter.paraUsuarioDTO(usuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody UsuarioCadastroDTO usuarioCadastroDTO)
			throws UsuarioNaoEncontradoException {
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);
		return usuarioConverter.paraUsuarioDTO(usuario);
	}

	@PutMapping("/{idUsuario}")
	public UsuarioDTO atualizar(@PathVariable Long idUsuario,
			@Valid @RequestBody UsuarioAtualizarDTO usuarioCadastroDTO) {
		Usuario usuario = usuarioService.atualizar(usuarioCadastroDTO, idUsuario);

		return usuarioConverter.paraUsuarioDTO(usuario);
	}

	@DeleteMapping("/{idUsuario}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long idUsuario) {
		usuarioService.remover(idUsuario);
	}

}
