package br.com.wepes.masterleague.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wepes.masterleague.api.converter.JogadorConverter;
import br.com.wepes.masterleague.api.model.atualizar.JogadorAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.NegociacaoCadastroDTO;
import br.com.wepes.masterleague.api.model.view.NegociacaoViewDTO;
import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.Jogador;
import br.com.wepes.masterleague.domain.Negociacao;
import br.com.wepes.masterleague.domain.enums.TipoTransferenciaEnum;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.repositories.NegociacaoRepository;
import br.com.wepes.masterleague.services.impl.NegociacaoServiceImpl;

@Service
public class NegociacaoService implements NegociacaoServiceImpl {

	private static final String ID_CLUBE_NAO_ENCONTRADA = "Negociacao de id %d, não foi encontrado";

	@Autowired
	private NegociacaoRepository negociacaoRepository;

	@Autowired
	private ClubeService clubeService;

	@Autowired
	private JogadorService jogadorService;

	@Autowired
	private JogadorConverter jogadorConverter;

	@Override
	public NegociacaoViewDTO salvar(NegociacaoCadastroDTO negociacaoCadastroDTO) {
		Clube clubeOrigem = clubeService.buscarPorId(negociacaoCadastroDTO.getIdClubeOrigem());
		Clube clubeDestino = clubeService.buscarPorId(negociacaoCadastroDTO.getIdClubeDestino());

		Jogador jogador = jogadorService.buscarPorId(negociacaoCadastroDTO.getIdJogador());
		validarValorEmCaixa(clubeDestino, jogador);
		atualizarCaixaDosClubes(clubeOrigem, clubeDestino, jogador);
		validarTipoDeNegociacao(negociacaoCadastroDTO, clubeDestino, jogador);
		Negociacao negociacao = converterParaNegociacao(negociacaoCadastroDTO, clubeOrigem, clubeDestino, jogador);

		return converterParaNegociacaoViewDTO(negociacaoRepository.save(negociacao));
	}

	private void validarTipoDeNegociacao(NegociacaoCadastroDTO negociacaoCadastroDTO, Clube clubeDestino,
			Jogador jogador) {
		JogadorAtualizarDTO jogadorAtualizarDTO = new JogadorAtualizarDTO();
		if (negociacaoCadastroDTO.getTipo().equals(TipoTransferenciaEnum.TRANSFERENCIA)) {
			jogador.setContrato(3);
		} else {
			jogador.setContrato(1);
		}

		jogadorAtualizarDTO = jogadorConverter.paraJogadorAtualizarDTO(jogador);
		jogadorAtualizarDTO.setClube(clubeDestino);
		jogadorAtualizarDTO
		.setSalario(BigDecimal.valueOf(calcularSalario(jogador)).setScale(0, RoundingMode.HALF_UP));
		clubeDestino.getJogadores().add(jogadorService.atualizar(jogadorAtualizarDTO, jogador.getId()));
	}

	private double calcularSalario(Jogador jogador) {
		double valorCalculadoSalario = 0;
		Random rand = new Random();
		int randomNum = 0;
		double dividido = 0.0;

		if (jogador.getOverall() >= 85) {
			randomNum = rand.nextInt((42 - 10) + 1) + 10;
			dividido = randomNum * 0.001;
			valorCalculadoSalario = ((BigDecimal) jogador.getValorDeMercado()).intValue() * dividido;
		} else if (jogador.getOverall() >= 78) {
			randomNum = rand.nextInt((34 - 15) + 1) + 15;
			valorCalculadoSalario = ((BigDecimal) jogador.getValorDeMercado()).intValue() * (randomNum / 1000);
		} else {
			randomNum = rand.nextInt((30 - 20) + 1) + 20;
			valorCalculadoSalario = ((BigDecimal) jogador.getValorDeMercado()).intValue() * (randomNum / 1000);
		}

		return valorCalculadoSalario;
	}

	private void atualizarCaixaDosClubes(Clube clubeOrigem, Clube clubeDestino, Jogador jogador) {
		clubeDestino.setCaixa(clubeDestino.getCaixa().subtract(jogador.getValorDeMercado()));
		clubeOrigem.setCaixa(clubeOrigem.getCaixa().add(jogador.getValorDeMercado()));
	}

	private void validarValorEmCaixa(Clube clubeDestino, Jogador jogador) {
		if (clubeDestino.getCaixa().compareTo(jogador.getValorDeMercado()) != 1) {
			throw new NegocioException("Clube necessita de mais dinheiro para efetuar a transação");
		}
	}

	private Negociacao converterParaNegociacao(NegociacaoCadastroDTO negociacaoCadastroDTO, Clube clubeOrigem,
			Clube clubeDestino, Jogador jogador) {
		Negociacao negociacao = new Negociacao();
		negociacao.setClubeDestino(clubeDestino);
		negociacao.setClubeOrigem(clubeOrigem);
		negociacao.setTipo(negociacaoCadastroDTO.getTipo());
		negociacao.setValorTransacionado(jogador.getValorDeMercado());
		negociacao.setJogador(jogadorService.buscarPorId(negociacaoCadastroDTO.getIdJogador()));
		return negociacao;
	}

	@Override
	public NegociacaoViewDTO buscarPorId(Long idNegociacao) {
		Negociacao negociacao = negociacaoRepository.findById(idNegociacao).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(ID_CLUBE_NAO_ENCONTRADA, idNegociacao)));

		return converterParaNegociacaoViewDTO(negociacao);
	}

	@Override
	public List<NegociacaoViewDTO> buscar() {
		List<NegociacaoViewDTO> listaNegociacaoViewDTO = new ArrayList<>();
		List<Negociacao> listaNegociacao = negociacaoRepository.findAll();

		listaNegociacaoViewDTO = converterParaListaNegociacaoViewDTO(listaNegociacao);
		return listaNegociacaoViewDTO;
	}

	private NegociacaoViewDTO converterParaNegociacaoViewDTO(Negociacao negociacao) {
		NegociacaoViewDTO negociacaoView = new NegociacaoViewDTO();
		negociacaoView.setId(negociacao.getId());
		negociacaoView.setTipo(negociacao.getTipo());
		negociacaoView.setClubeDestino(negociacao.getClubeDestino().getNome());
		negociacaoView.setClubeOrigem(negociacao.getClubeOrigem().getNome());
		negociacaoView.setValorTransacionado(negociacao.getValorTransacionado());
		negociacaoView.setJogador(negociacao.getJogador().getNome());

		return negociacaoView;
	}

	private List<NegociacaoViewDTO> converterParaListaNegociacaoViewDTO(List<Negociacao> listaNegociacao) {
		List<NegociacaoViewDTO> listaNegociacaoView = new ArrayList<>();
		listaNegociacao.forEach(negociacao -> listaNegociacaoView.add(converterParaNegociacaoViewDTO(negociacao)));

		return listaNegociacaoView;
	}
}
