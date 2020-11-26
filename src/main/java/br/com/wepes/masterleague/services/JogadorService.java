package br.com.wepes.masterleague.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.wepes.masterleague.api.converter.JogadorConverter;
import br.com.wepes.masterleague.api.model.atualizar.JogadorAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.JogadorCadastroDTO;
import br.com.wepes.masterleague.domain.Jogador;
import br.com.wepes.masterleague.domain.enums.PosicaoEnum;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.repositories.JogadorRepository;
import br.com.wepes.masterleague.services.impl.JogadorServiceImpl;

@Service
public class JogadorService implements JogadorServiceImpl {

	private static final String NOME_JOGADOR_JA_CADASTRADO = "Jogador de nome %s, já existe nos cadastros";

	private static final String ID_JOGADOR_NAO_ENCONTRADA = "Jogador de id %d, não foi encontrado";

	@Autowired
	private JogadorRepository jogadorRepository;

	@Autowired
	private JogadorConverter jogadorConverter;

	@Override
	public Jogador salvar(JogadorCadastroDTO jogadorCadastroDTO) {
		validarCadastro(jogadorCadastroDTO);
		Jogador jogador = new Jogador();
		jogador = jogadorConverter.paraJogador(jogadorCadastroDTO);

		double valorCalculado = 0;

		valorCalculado = valorPorPosicao(valorCalculado, jogador);
		valorCalculado = valorCalculadoPorIdade(valorCalculado, jogador);
		valorCalculado = valorCalculadoPorOverall(valorCalculado, jogador.getOverall(), jogador.getIdade(),
				jogador.getPosicao()) * 1000000;

		jogador.setValorDeMercado(BigDecimal.valueOf(valorCalculado).setScale(0, RoundingMode.HALF_UP));

		return jogadorRepository.save(jogador);
	}

	@Override
	public Jogador buscarPorId(Long idJogador) {
		return jogadorRepository.findById(idJogador).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(ID_JOGADOR_NAO_ENCONTRADA, idJogador)));
	}

	@Override
	public void remover(Long idJogador) {
		try {
			jogadorRepository.deleteById(idJogador);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(ID_JOGADOR_NAO_ENCONTRADA, idJogador));
		}
	}

	@Override
	public Jogador atualizar(@Valid JogadorAtualizarDTO jogadorAtualizarDTO, Long idJogador) {
		Jogador jogadorAtualizar = buscarPorId(idJogador);

		validarAtualizacao(jogadorAtualizarDTO, jogadorAtualizar);
		jogadorAtualizar = jogadorConverter.paraJogador(jogadorAtualizarDTO);
		jogadorAtualizar.setId(idJogador);

		double valorCalculado = 0;

		valorCalculado = valorPorPosicao(valorCalculado, jogadorAtualizar);
		valorCalculado = valorCalculadoPorIdade(valorCalculado, jogadorAtualizar);
		valorCalculado = valorCalculadoPorOverall(valorCalculado, jogadorAtualizar.getOverall(),
				jogadorAtualizar.getIdade(), jogadorAtualizar.getPosicao()) * 1000000;

		jogadorAtualizar.setValorDeMercado(BigDecimal.valueOf(valorCalculado).setScale(0, RoundingMode.HALF_UP));

		return jogadorRepository.save(jogadorAtualizar);
	}

	private void validarAtualizacao(JogadorAtualizarDTO jogadorAtualizarDTO, Jogador jogadorAtualizar) {
		Optional<Jogador> jogadorOptional = jogadorRepository.findByNomeIgnoreCase(jogadorAtualizarDTO.getNome());
		if (jogadorOptional.isPresent() && !jogadorOptional.get().getId().equals(jogadorAtualizar.getId())) {
			throw new NegocioException(String.format(NOME_JOGADOR_JA_CADASTRADO, jogadorAtualizarDTO.getNome()));
		}
	}

	private void validarCadastro(JogadorCadastroDTO jogadorCadastroDTO) {
		Optional<Jogador> jogadorOptional = jogadorRepository.findByNomeIgnoreCase(jogadorCadastroDTO.getNome());
		if (jogadorOptional.isPresent() || jogadorCadastroDTO.getNome().isEmpty()) {
			throw new NegocioException(String.format(NOME_JOGADOR_JA_CADASTRADO, jogadorCadastroDTO.getNome()));
		}
	}

	@Override
	public List<Jogador> buscar() {
		return jogadorRepository.findAll();
	}

	private static double valorCalculadoPorIdade(double valorCalculado, Jogador j) {
		if (j.getIdade() <= 18) {
			valorCalculado += (j.getOverall() * 0.38);
		} else if (j.getIdade() <= 20) {
			valorCalculado += (j.getOverall() * 0.54);
		} else if (j.getIdade() <= 25) {
			valorCalculado += (j.getOverall() * 1.20);
		} else if (j.getIdade() <= 29) {
			valorCalculado += (j.getOverall() * 0.73);
		} else if (j.getIdade() <= 32) {
			valorCalculado += (j.getOverall() * 0.60);
		} else if (j.getIdade() <= 36) {
			valorCalculado += (j.getOverall() * 0.30);
		} else if (j.getIdade() <= 40) {
			valorCalculado += (j.getOverall() * 0.10);
		} else if (j.getIdade() > 40) {
			valorCalculado += (j.getOverall() * 0.05);
		}
		return valorCalculado;
	}

	private static double valorPorPosicao(double valorCalculado, Jogador j) {
		if (j.getPosicao().equals(PosicaoEnum.ATACANTE)) {
			valorCalculado = (j.getOverall() * 1.17);
		} else if (j.getPosicao().equals(PosicaoEnum.MEIO_CAMPO)) {
			valorCalculado = (j.getOverall() * 0.92);
		} else if (j.getPosicao().equals(PosicaoEnum.VOLANTE)) {
			valorCalculado = (j.getOverall() * 0.77);
		} else if (j.getPosicao().equals(PosicaoEnum.LATERAL_ESQUERDO)
				|| j.getPosicao().equals(PosicaoEnum.LATERAL_DIREITO)) {
			valorCalculado = (j.getOverall() * 0.6);
		} else if (j.getPosicao().equals(PosicaoEnum.ZAGUEIRO)) {
			valorCalculado = (j.getOverall() * 0.48);
		} else if (j.getPosicao().equals(PosicaoEnum.GOLEIRO)) {
			valorCalculado = (j.getOverall() * 0.15);
		}
		return valorCalculado;
	}

	private int valorCalculadoPorOverall(Double valorCalculado, Integer overall, Integer idade, PosicaoEnum posicao) {
		if (overall < 81 && idade < 26) {
			valorCalculado = valorCalculado / 4;
		} else if (overall < 86 && idade < 25) {
			valorCalculado = valorCalculado / 1.85;
		} else if (overall < 88 && idade < 25) {
			valorCalculado = valorCalculado / 1.5;
		} else if (overall < 86 && idade < 28) {
			valorCalculado = valorCalculado / 2;
		} else if (overall < 80 && idade < 50) {
			valorCalculado = valorCalculado / 1.93;
		}

		if (overall >= 85 && idade < 37 && (posicao.equals(PosicaoEnum.GOLEIRO) || posicao.equals(PosicaoEnum.ZAGUEIRO)
				|| posicao.equals(PosicaoEnum.LATERAL_DIREITO) || posicao.equals(PosicaoEnum.LATERAL_ESQUERDO))) {
			valorCalculado = valorCalculado * 1.42;
		}

		if ((overall <= 89 || overall >= 80)
				&& (posicao.equals(PosicaoEnum.MEIO_CAMPO) || posicao.equals(PosicaoEnum.VOLANTE))) {
			valorCalculado = valorCalculado / 1.27;
		}
		
		if (overall >= 90) {
			valorCalculado = valorCalculado * 1.12;
		}

		return valorCalculado.intValue();
	}
}
