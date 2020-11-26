package br.com.wepes.masterleague.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.wepes.masterleague.api.model.cadastro.PartidaCadastroDTO;
import br.com.wepes.masterleague.api.model.view.PartidaViewDTO;
import br.com.wepes.masterleague.domain.Partida;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.repositories.PartidaRepository;

@Service
public class PartidaService implements PartidaServiceImpl {

	private static final String ID_PARTIDA_NAO_ENCONTRADA = "Partida de id %d, não foi encontrado";

	@Autowired
	private PartidaRepository partidaRepository;

	@Autowired
	private ClubeService clubeService;

	@Override
	public PartidaViewDTO salvar(PartidaCadastroDTO partidaCadastroDTO) {

		Partida partida = partidaRepository.save(converterParaPartida(partidaCadastroDTO));

		return converterParaPartidaViewDTO(partida);
	}

	private PartidaViewDTO converterParaPartidaViewDTO(Partida partida) {
		PartidaViewDTO partidaViewDTO = new PartidaViewDTO();
		partidaViewDTO.setId(partida.getId());
		partidaViewDTO.setPublico(partida.getPublico());
		partidaViewDTO.setRenda(partida.getRenda());
		partidaViewDTO.setClubeMandante(partida.getClubeMandante().getNome());
		partidaViewDTO.setClubeVisitante(partida.getClubeVisitante().getNome());
		partidaViewDTO.getGols().addAll(partida.getGols());
		partidaViewDTO.getAssistencias().addAll(partida.getAssistencias());

		return partidaViewDTO;
	}

	private Partida converterParaPartida(PartidaCadastroDTO partidaCadastroDTO) {
		Partida partida = new Partida();
		partida.getAssistencias().addAll(partidaCadastroDTO.getAssistencias());
		partida.getGols().addAll(partidaCadastroDTO.getGols());
		partida.setClubeMandante(clubeService.buscarPorId(partidaCadastroDTO.getIdClubeMandante()));
		partida.setClubeVisitante(clubeService.buscarPorId(partidaCadastroDTO.getIdClubeVisitante()));
		partida.setPublico(gerarPublico());
		partida.setRenda(calcularRenda(partida.getPublico()));

		return partida;
	}

	@Override
	public PartidaViewDTO buscarPorId(Long idPartida) {
		Partida partida = partidaRepository.findById(idPartida).orElseThrow(
				() -> new EntidadeNaoEncontradaException(String.format(ID_PARTIDA_NAO_ENCONTRADA, idPartida)));

		return converterParaPartidaViewDTO(partida);
	}

	@Override
	public void remover(Long idPartida) {
		try {
			partidaRepository.deleteById(idPartida);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(ID_PARTIDA_NAO_ENCONTRADA, idPartida));
		}
	}

	@Override
	public List<PartidaViewDTO> buscar() {
		List<Partida> listaPartidas = partidaRepository.findAll();

		return converterListPartidaViewDTOs(listaPartidas);
	}

	private List<PartidaViewDTO> converterListPartidaViewDTOs(List<Partida> listaPartidas) {
		List<PartidaViewDTO> listaPartidasViewDTOs = new ArrayList<>();
		listaPartidas.forEach(partida -> listaPartidasViewDTOs.add(converterParaPartidaViewDTO(partida)));

		return listaPartidasViewDTOs;
	}
	
	private Integer gerarPublico() {
		// TODO Auto-generated method stub
		return null;
	}

	private BigDecimal calcularRenda(Integer publico) {
		// TODO Auto-generated method stub
		return null;
	}
}
