package br.com.wepes.masterleague.domain.exceptions;

public class ClubeNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public ClubeNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public ClubeNaoEncontradoException(Long idClube) {
		this(String.format("Não existe Clube com id %s, por favor verifique!", idClube));
	}
}
