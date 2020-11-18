package br.com.wepes.masterleague.domain.exceptions;

public class UsuarioNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public UsuarioNaoEncontradoException(Long idUsuario) {
		this(String.format("Não existe Usuário com id %s, por favor verifique!", idUsuario));
	}
}
