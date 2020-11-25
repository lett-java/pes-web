package br.com.wepes.masterleague.domain.exceptions;

public class NegociacaoNaoEncontradoException extends Exception {
	private static final long serialVersionUID = 1L;

	public NegociacaoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public NegociacaoNaoEncontradoException(Long idNegociacao) {
		this(String.format("Não existe Negociacao com id %s, por favor verifique!", idNegociacao));
	}
}
