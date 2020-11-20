package br.com.wepes.masterleague;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import br.com.wepes.masterleague.api.converter.UsuarioConverter;
import br.com.wepes.masterleague.api.exceptionhandler.ApiExceptionHandler;
import br.com.wepes.masterleague.api.model.atualizar.UsuarioAtualizarDTO;
import br.com.wepes.masterleague.api.model.cadastro.ClubeCadastroDTO;
import br.com.wepes.masterleague.api.model.cadastro.UsuarioCadastroDTO;
import br.com.wepes.masterleague.controller.UsuarioController;
import br.com.wepes.masterleague.domain.Usuario;
import br.com.wepes.masterleague.domain.exceptions.EntidadeEmUsoException;
import br.com.wepes.masterleague.domain.exceptions.EntidadeNaoEncontradaException;
import br.com.wepes.masterleague.domain.exceptions.NegocioException;
import br.com.wepes.masterleague.services.ClubeService;
import br.com.wepes.masterleague.services.UsuarioService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:clear_table.sql")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioTests {

	private static final long ID_MOCK = 100L;

	private static final long ID_INVALIDA = 1L;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ClubeService clubeService;

	@Mock
	private UsuarioService usuarioServiceMock;

	@InjectMocks
	private UsuarioController usuarioController;

	@InjectMocks
	private ApiExceptionHandler apiExceptionHandler;
	
	@Mock
	private UsuarioConverter usuarioConverterMock;
	
	@LocalServerPort
	private int port;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/usuarios";

		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssuredMockMvc.standaloneSetup(usuarioController, apiExceptionHandler);
		RestAssuredMockMvc.basePath = "/usuarios";
	}

	@Test
	public void deveOcorrerSucesso_QuandoBuscarTodosOsUsuarios() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);

		List<Usuario> listaUsuarios = new ArrayList<>();

		listaUsuarios.add(usuario);

		assertEquals(usuarioService.buscar().get(0).getId(), listaUsuarios.get(0).getId());
		assertEquals(usuarioService.buscar().get(0).getNome(), listaUsuarios.get(0).getNome());
	}

	@Test
	public void deveOcorrerSucesso_QuandoBuscarPorId() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);

		Usuario usuarioConsultado = usuarioService.buscarPorId(usuario.getId());

		assertEquals(usuarioService.buscarPorId(usuario.getId()).getId(), usuarioConsultado.getId());
		assertEquals(usuarioService.buscarPorId(usuario.getId()).getNome(), usuarioConsultado.getNome());
	}

	@Test
	public void deveFalhar_QuandoBuscarUsuarioPorIdInvalida() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.buscarPorId(ID_INVALIDA));
	}

	@Test
	public void deveFalhar_QuandoCriarUsuario() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		usuarioCadastroDTO.setNome("");

		assertThrows(NegocioException.class, () -> usuarioService.salvar(usuarioCadastroDTO));
	}

	@Test
	public void deveOcorrerSucesso_QuandoAtualizarUsuario() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuarioSalvo = usuarioService.salvar(usuarioCadastroDTO);

		UsuarioAtualizarDTO usuarioAtualizarDTO = criarUsuarioAtualizarDTO();

		Usuario usuarioAtualizado = usuarioService.atualizar(usuarioAtualizarDTO, usuarioSalvo.getId());

		assertEquals(usuarioSalvo.getId(), usuarioAtualizado.getId());
		assertNotEquals(usuarioSalvo.getNome(), usuarioAtualizado.getNome());
	}

	@Test
	public void deveFalhar_QuandoAtualizarUsuarioComNomeJaExistenteNoBanco() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuarioSalvo = usuarioService.salvar(usuarioCadastroDTO);

		UsuarioCadastroDTO usuarioCadastroDTO1 = criarUsuarioCadastroDTO();
		usuarioCadastroDTO1.setNome("Fabio Lettieri");
		usuarioService.salvar(usuarioCadastroDTO1);

		UsuarioAtualizarDTO usuarioAtualizarDTO = criarUsuarioAtualizarDTO();

		assertThrows(NegocioException.class, () -> usuarioService.atualizar(usuarioAtualizarDTO, usuarioSalvo.getId()));
	}

	@Test
	public void deveOcorrerSucesso_QuandoRemoverUsuario() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuarioSalvo = usuarioService.salvar(usuarioCadastroDTO);

		assertDoesNotThrow(() -> usuarioService.remover(usuarioSalvo.getId()));
	}

	@Test
	public void deveFalhar_QuandoRemoverUsuarioComIdInexistente() {
		assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.remover(ID_INVALIDA));
	}

	@Test
	public void deveFalhar_QuandoRemoverUsuarioVinculadoEmClube() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);
		
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO(usuario.getId());
		clubeService.salvar(clubeCadastroDTO);

		assertThrows(EntidadeEmUsoException.class, () -> usuarioService.remover(usuario.getId()));

	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarUsuarios() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarUsuarioPorId() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);
		when(usuarioServiceMock.buscarPorId(usuario.getId())).thenReturn(usuario);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/" + usuario.getId())
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarUsuarioPorIdInvalida() {
		when(usuarioServiceMock.buscarPorId(ID_MOCK)).thenThrow(EntidadeNaoEncontradaException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/" + ID_MOCK)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}

	@Test
	public void deveRetornarStatus201_QuandoCriarUsuario() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);
		
		when(usuarioServiceMock.salvar(usuarioCadastroDTO)).thenReturn(usuario);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(usuarioCadastroDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCriarUsuarioComAtributoInvalido() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTOInvalido();
		
		when(usuarioServiceMock.salvar(usuarioCadastroDTO)).thenThrow(NegocioException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(usuarioCadastroDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus200_QuandoAtualizarUsuario() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuarioSalvo = usuarioService.salvar(usuarioCadastroDTO);
		
		UsuarioAtualizarDTO usuarioAtualizarDTO = criarUsuarioAtualizarDTO();
		Usuario usuarioAtualizado= usuarioService.atualizar(usuarioAtualizarDTO, usuarioSalvo.getId());
		
		when(usuarioServiceMock.atualizar(usuarioAtualizarDTO, usuarioSalvo.getId())).thenReturn(usuarioAtualizado);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(usuarioAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + usuarioSalvo.getId())
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoAtualizarUsuarioComIdInvalido() {
		UsuarioAtualizarDTO usuarioAtualizarDTO = criarUsuarioAtualizarDTO();
		when(usuarioServiceMock.atualizar(usuarioAtualizarDTO, ID_INVALIDA)).thenThrow(EntidadeNaoEncontradaException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(usuarioAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + ID_INVALIDA)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoAtualizarUsuarioComNomeJaExistente() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuarioSalvo = usuarioService.salvar(usuarioCadastroDTO);
		
		UsuarioCadastroDTO usuarioCadastroDTO1 = criarUsuarioCadastroDTO();
		usuarioCadastroDTO1.setNome("Fabio Lettieri");
		usuarioService.salvar(usuarioCadastroDTO1);
		
		UsuarioAtualizarDTO usuarioAtualizarDTO = criarUsuarioAtualizarDTO();
		
		when(usuarioServiceMock.atualizar(usuarioAtualizarDTO, usuarioSalvo.getId())).thenThrow(NegocioException.class);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.body(usuarioAtualizarDTO)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/" + usuarioSalvo.getId())
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus204_QuandoExcluirUsuario() {
		doNothing().when(usuarioServiceMock).remover(ID_MOCK);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.delete("/" + ID_MOCK)
		.then()
			.statusCode(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	public void deveRetornarStatus404_QuandoExcluirUsuarioComIdInexistente() {
		doThrow(EntidadeNaoEncontradaException.class).when(usuarioServiceMock).remover(ID_INVALIDA);
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.delete("/" + ID_INVALIDA)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoExcluirUsuarioVinculadoEmClube() {
		UsuarioCadastroDTO usuarioCadastroDTO = criarUsuarioCadastroDTO();
		Usuario usuario = usuarioService.salvar(usuarioCadastroDTO);
		
		ClubeCadastroDTO clubeCadastroDTO = criarClubeCadastroDTO(usuario.getId());
		clubeService.salvar(clubeCadastroDTO);
		
		doThrow(EntidadeEmUsoException.class).when(usuarioServiceMock).remover(usuario.getId());
		
		io.restassured.module.mockmvc.RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.delete("/" + usuario.getId())
		.then()
			.statusCode(HttpStatus.CONFLICT.value());
	}
	
	private UsuarioCadastroDTO criarUsuarioCadastroDTOInvalido() {
		UsuarioCadastroDTO usuarioCadastroDTO = new UsuarioCadastroDTO();
		return usuarioCadastroDTO;
	}

	private ClubeCadastroDTO criarClubeCadastroDTO(Long idUsuario) {
		ClubeCadastroDTO clubeCadastroDTO = new ClubeCadastroDTO();
		clubeCadastroDTO.setNome("Santos FC");
		clubeCadastroDTO.setIdTreinador(idUsuario);

		return clubeCadastroDTO;
	}

	private UsuarioAtualizarDTO criarUsuarioAtualizarDTO() {
		UsuarioAtualizarDTO usuarioAtualizarDTO = new UsuarioAtualizarDTO();
		usuarioAtualizarDTO.setNome("Fabio Lettieri");

		return usuarioAtualizarDTO;
	}

	private UsuarioCadastroDTO criarUsuarioCadastroDTO() {
		UsuarioCadastroDTO usuarioCadastroDTO = new UsuarioCadastroDTO();
		usuarioCadastroDTO.setNome("Fabio");

		return usuarioCadastroDTO;
	}

}
