package br.com.wepes.masterleague.domain.exceptions;

public class SenhaObrigatoriaUsuarioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SenhaObrigatoriaUsuarioException(String message) {
		super(message);
	}
}
