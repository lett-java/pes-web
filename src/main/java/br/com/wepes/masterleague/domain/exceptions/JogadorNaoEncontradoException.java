package br.com.wepes.masterleague.domain.exceptions;

public class JogadorNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public JogadorNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public JogadorNaoEncontradoException(Long idJogador) {
		this(String.format("Não existe Jogador com id %s, por favor verifique!", idJogador));
	}
}
