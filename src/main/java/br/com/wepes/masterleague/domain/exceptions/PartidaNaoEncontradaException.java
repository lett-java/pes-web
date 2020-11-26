package br.com.wepes.masterleague.domain.exceptions;

public class PartidaNaoEncontradaException extends Exception {
	private static final long serialVersionUID = 1L;

	public PartidaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public PartidaNaoEncontradaException(Long idPartida) {
		this(String.format("Não existe Partida com id %s, por favor verifique!", idPartida));
	}
}
