package br.com.wepes.masterleague.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.wepes.masterleague.api.converter.UsuarioConverter;
import br.com.wepes.masterleague.api.model.atualizar.UsuarioAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.UsuarioCadastroDTO;
import br.com.wepes.masterleague.domain.Usuario;
import br.com.wepes.masterleague.domain.exceptions.EntidadeEmUsoException;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.repositories.UsuarioRepository;
import br.com.wepes.masterleague.services.impl.UsuarioServiceImpl;

@Service
public class UsuarioService implements UsuarioServiceImpl {

	private static final String NOME_USUARIO_JA_CADASTRADO = "Usuário de nome %s, já existe nos cadastros";

	private static final String ID_USUARIO_NAO_ENCONTRADA = "Usuário de id %d, não foi encontrado";

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioConverter usuarioConverter;
	
	@Override
	public Usuario salvar(UsuarioCadastroDTO usuarioCadastroDTO) {
		validarCadastro(usuarioCadastroDTO);
		Usuario usuario = usuarioConverter.paraUsuario(usuarioCadastroDTO);
		validarPassword(usuarioCadastroDTO.getPassword(), usuarioCadastroDTO.getValidarPassword());
		usuario.setPassword(criptografarSenha(usuarioCadastroDTO.getPassword()));
		
		return usuarioRepository.save(usuario);
	}

	private void validarPassword(String password, String validarPassword) {
		if(!password.equals(validarPassword)) {
			throw new NegocioException("Por favor, informe senhas iguais!");
		}
	}

	@Override
	public Usuario buscarPorId(Long idUsuario) {
		return usuarioRepository.findById(idUsuario).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(ID_USUARIO_NAO_ENCONTRADA, idUsuario)));
	}

	@Override
	public void remover(Long idUsuario) {
		try {
			usuarioRepository.deleteById(idUsuario);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(ID_USUARIO_NAO_ENCONTRADA, idUsuario));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Usuário de id %d, não pode ser deletado pois está em uso", idUsuario));
		}
	}
	@Override
	public Usuario atualizar(@Valid UsuarioAtualizarDTO usuarioAtualizarDTO, Long idUsuario) {
		Usuario usuarioAtualizar = buscarPorId(idUsuario);

		validarAtualizacao(usuarioAtualizarDTO, usuarioAtualizar);
		usuarioAtualizar = usuarioConverter.paraUsuario(usuarioAtualizarDTO);
		usuarioAtualizar.setId(idUsuario);

		return usuarioRepository.save(usuarioAtualizar);
	}

	private void validarAtualizacao(UsuarioAtualizarDTO usuarioAtualizarDTO, Usuario usuarioAtualizar) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByNomeIgnoreCase(usuarioAtualizarDTO.getNome());
		if (usuarioOptional.isPresent() && !usuarioOptional.get().getId().equals(usuarioAtualizar.getId())) {
			throw new NegocioException(String.format(NOME_USUARIO_JA_CADASTRADO, usuarioAtualizarDTO.getNome()));
		}
	}

	private void validarCadastro(UsuarioCadastroDTO usuarioCadastroDTO) {
		Optional<Usuario> usuarioOptional = usuarioRepository.findByNomeIgnoreCase(usuarioCadastroDTO.getNome());
		if (usuarioOptional.isPresent() || usuarioCadastroDTO.getNome().isEmpty()) {
			throw new NegocioException(String.format(NOME_USUARIO_JA_CADASTRADO, usuarioCadastroDTO.getNome()));
		}
	}
	
	private String criptografarSenha(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}


	@Override
	public List<Usuario> buscar() {
		return usuarioRepository.findAll();
	}
}
