package br.com.wepes.masterleague;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.wepes.masterleague.api.converter.ClubeConverter;
import br.com.wepes.masterleague.api.exceptionhandler.ApiExceptionHandler;
import br.com.wepes.masterleague.api.model.atualizar.ClubeAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.controller.ClubeController;
import br.com.wepes.masterleague.domain.Clube;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.services.ClubeService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clear_table.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClubeTests {

	private static final long ID_MOCK = 2L;

	private static final long ID_NAO_CADASTRADA = 100L;

	@Autowired
	private ClubeService clubeService;
	
	@InjectMocks
	private ClubeController clubeController;
	
	@InjectMocks
	private ApiExceptionHandler apiExceptionHandler;
	
	@Mock
	private ClubeService clubeServiceMock;
	
	@Mock
	private ClubeConverter clubeConverter;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/clubes";

		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.standaloneSetup(clubeController, apiExceptionHandler);
		RestAssuredMockMvc.basePath = "/clubes";
	}

	@Test
	public void deveOcorrerSucesso_QuandoConsultarTodosOsClubes() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);

		List<Clube> listaClubes = new ArrayList<>();
		listaClubes.add(clube);

		assertEquals(clubeService.buscar().get(0).getId(), listaClubes.get(0).getId());
		assertEquals(clubeService.buscar().get(0).getNome(), listaClubes.get(0).getNome());
	}

	@Test
	public void deveOcorrerSucesso_QuandoConsultarClube() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);

		assertEquals(clubeService.buscarPorId(clube.getId()), clube);
		assertEquals(clubeService.buscarPorId(clube.getId()).getNome(), clube.getNome());
		assertEquals(clubeService.buscarPorId(clube.getId()).getId(), clube.getId());
	}

	@Test
	public void deveFalhar_QuandoConsultarPorIdInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> clubeService.buscarPorId(ID_NAO_CADASTRADA));
	}

	@Test
	public void deveFalhar_QuandoCadastrarClubeComAtributoInvalido() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		clubeCadastroDTO.setNome("");

		assertThrows(NegocioException.class, () -> clubeService.salvar(clubeCadastroDTO));
	}

	@Test
	public void deveOcorrerSucesso_QuandoAtualizarClube() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clubeSalvo = clubeService.salvar(clubeCadastroDTO);

		ClubeAtualizarDTO clubeAtualizarDTO = criarClubeAtualizarDTO();
		Clube clubeAtualizado = clubeService.atualizar(clubeAtualizarDTO, clubeSalvo.getId());

		assertEquals(clubeService.atualizar(clubeAtualizarDTO, clubeSalvo.getId()), clubeAtualizado);
		assertEquals(clubeService.atualizar(clubeAtualizarDTO, clubeSalvo.getId()).getId(), clubeAtualizado.getId());
	}

	@Test
	public void deveFalhar_QuandoAtualizarClubeComNomeJaCadastrado() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clubeSalvo = clubeService.salvar(clubeCadastroDTO);

		ClubeCadastroDTO clubeCadastroDTO1 = criarClubeCadastroDTO();
		clubeCadastroDTO1.setNome("Santos Futebol Clube");
		clubeService.salvar(clubeCadastroDTO1);

		ClubeAtualizarDTO clubeAtualizarDTO = criarClubeAtualizarDTO();

		assertThrows(NegocioException.class, () -> clubeService.atualizar(clubeAtualizarDTO, clubeSalvo.getId()));
	}

	@Test
	public void deveOcorrerSucesso_QuandoRemoverClube() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);

		assertDoesNotThrow(() -> clubeService.remover(clube.getId()));
	}
	
	@Test
	public void deveFalhar_QuandoExcluirClubeComIdInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> clubeService.remover(ID_NAO_CADASTRADA));
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarClubes() {
		List<Clube> listaClubes = new ArrayList<>();
		when(clubeServiceMock.buscar()).thenReturn(listaClubes);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarClubePorId() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);
		
		when(clubeServiceMock.buscarPorId(ID_MOCK)).thenReturn(clube);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/" + ID_MOCK)
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarClubePorIdInvalida() {
		when(clubeServiceMock.buscarPorId(ID_NAO_CADASTRADA)).thenThrow(EntidadeNaoEncontradaException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/" + ID_NAO_CADASTRADA)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCriarClube() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);
		
		when(clubeServiceMock.salvar(clubeCadastroDTO)).thenReturn(clube);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(clubeCadastroDTO)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCriarClubeComAtributoInvalido() {
		ClubeCadastroDTO clubeCadastroDTO = new ClubeCadastroDTO();
		clubeCadastroDTO.setNome("");
		
		when(clubeServiceMock.salvar(clubeCadastroDTO)).thenThrow(NegocioException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(clubeCadastroDTO)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarClube() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clube = clubeService.salvar(clubeCadastroDTO);
		
		ClubeAtualizarDTO clubeAtualizarDTO = criarClubeAtualizarDTO();
		Clube clubeAtualizado = clubeService.atualizar(clubeAtualizarDTO, clube.getId());
		
		when(clubeServiceMock.atualizar(clubeAtualizarDTO, clube.getId())).thenReturn(clubeAtualizado);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(clubeAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + clube.getId())
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoAtualizarClubeComIdInexistente() {
		ClubeAtualizarDTO clubeAtualizarDTO = criarClubeAtualizarDTO();
		
		when(clubeServiceMock.atualizar(clubeAtualizarDTO, ID_NAO_CADASTRADA)).thenThrow(EntidadeNaoEncontradaException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(clubeAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + ID_NAO_CADASTRADA)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarClubeComNomeJaCadastrado() {
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO();
		Clube clubeSalvo = clubeService.salvar(clubeCadastroDTO);
		
		ClubeCadastroDTO clubeCadastroDTO1 = criarClubeCadastroDTO();
		clubeCadastroDTO1.setNome("Santos Futebol Clube");
		clubeService.salvar(clubeCadastroDTO1);
		
		ClubeAtualizarDTO clubeAtualizarDTO = criarClubeAtualizarDTO();
		
		when(clubeServiceMock.atualizar(clubeAtualizarDTO, clubeSalvo.getId())).thenThrow(NegocioException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(clubeAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + clubeSalvo.getId())
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoExcluirClube() {
		doNothing().when(clubeServiceMock).remover(ID_MOCK);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.delete("/" + ID_MOCK)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoExcluirClube() {
		doThrow(EntidadeNaoEncontradaException.class).when(clubeServiceMock).remover(ID_MOCK);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.delete("/" + ID_MOCK)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	private ClubeAtualizarDTO criarClubeAtualizarDTO() {
		ClubeAtualizarDTO clubeAtualizarDTO = new ClubeAtualizarDTO();
		clubeAtualizarDTO.setNome("Santos Futebol Clube");
		return clubeAtualizarDTO;
	}

	private ClubeCadastroDTO criarClubeCadastroDTO() {
		ClubeCadastroDTO clubeCadastroDTO = new ClubeCadastroDTO();
		clubeCadastroDTO.setNome("Santos FC");
		return clubeCadastroDTO;
	}

}
