package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.gateways.controller.UsuarioController;
import br.com.fiap.TechChallenger.handlers.ControllerExceptionHandler;
import br.com.fiap.TechChallenger.usecases.usuario.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("teste")
public class UsuarioControllerTest {
    @Mock
    CriarUsuario criarUsuario;

    @Mock
    EditarUsuario editarUsuario;

    @Mock
    BuscarUsuario buscarUsuario;

    @Mock
    DeletarUsuario deletarUsuario;

    @Mock
    TrocarSenha trocarSenha;

    MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        UsuarioController usuarioController = new UsuarioController(
                criarUsuario,
                editarUsuario,
                deletarUsuario,
                buscarUsuario, trocarSenha);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void teardown() throws Exception {
        mock.close();
    }


    @Nested
    public class BuscarUsuarioApi {
        @Test
        void devePermitirBuscarUsuario() throws Exception {
            // Given
            Long userId = 1L;
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setMethod("GET");
            UsuarioResponse usuarioResponse = new UsuarioResponse(
                    userId,
                    "John Doe",
                    "john@email",
                    "123456789",
                    TipoUsuario.DONO_RESTAURANTE
            );

            when(buscarUsuario.getUsuarioResponseById(userId)).thenReturn(ResponseEntity.ok(usuarioResponse));

            // When
            mockMvc.perform(get("/usuario/userId/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());

            // Then
            verify(buscarUsuario, times(1)).getUsuarioResponseById(userId);
        }
    }

    @Nested
    public class DeletarUsuarioApi {
        @Test
        void devePermitirDeletarUsuario() throws Exception {
            // Given
            Long userId = 1L;
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setMethod("DELETE");

            // When
            mockMvc.perform(delete("/usuario/userId/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());

            // Then
            verify(deletarUsuario, times(1)).deleteUsuarioById(userId);
        }
    }

    @Nested
    public class EditarUsuarioApi {
        @Test
        void devePermitirEditarUsuario() throws Exception {
            // Given
            Long userId = 1L;

            UsuarioEditDTO editarUsuarioRequest = new UsuarioEditDTO();
            editarUsuarioRequest.setNome("Jane Doe");
            editarUsuarioRequest.setEmail("jane@email");

            when(editarUsuario.editarUsuarioByUserId(editarUsuarioRequest, userId)).thenReturn(ResponseEntity.ok().build());

            // When
            mockMvc.perform(put("/usuario/userId/{userId}", userId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(asJsonString(editarUsuarioRequest)))
                    .andExpect(status().isOk());

            // Then
            verify(editarUsuario, times(1)).editarUsuarioByUserId(editarUsuarioRequest, userId);
        }
    }

    @Nested
    public class TrocarSenhaApi {

        @Test
        void devePermitirTrocarSenha() throws Exception {
            // Given
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setMethod("PUT");

            TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto();
            trocaSenhaDto.setSenhaAtual("senhaAtual");
            trocaSenhaDto.setNovaSenha("novaSenha");

            when(trocarSenha.execute(trocaSenhaDto)).thenReturn(ResponseEntity.ok().build());

            // When
            mockMvc.perform(put("/usuario/senha")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(asJsonString(trocaSenhaDto)))
                    .andExpect(status().isOk());

            // Then
            verify(trocarSenha, times(1)).execute(trocaSenhaDto);
        }

    }

    @Nested
    public class CriarUsuarioApi {
        @Test
        void devePermitirCriarUsuario() throws Exception {
            // Given
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setMethod("POST");

            UsuarioDTO criarUsuarioRequest = new UsuarioDTO();
            criarUsuarioRequest.setNome("John Doe");
            criarUsuarioRequest.setEmail("john@email");
            criarUsuarioRequest.setLogin("loginJohn");
            criarUsuarioRequest.setSenha("senhaJohn");
            criarUsuarioRequest.setEndereco(new ArrayList<>());
            criarUsuarioRequest.setTipoUsuario(TipoUsuario.DONO_RESTAURANTE);

            when(criarUsuario.criar(criarUsuarioRequest)).thenReturn(ResponseEntity.ok().build());

            // When
            mockMvc.perform(post("/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(asJsonString(criarUsuarioRequest)))
                    .andExpect(status().isOk());

            // Then
            verify(criarUsuario, times(1)).criar(criarUsuarioRequest);
        }

        @Test
        void deveRetornarBadRequestAoCriarUsuarioComDadosInvalidos() throws Exception {
            // Given
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.setMethod("POST");

            UsuarioDTO criarUsuarioRequest = new UsuarioDTO();
            criarUsuarioRequest.setNome("John Doe");
            criarUsuarioRequest.setEmail("john@email");
            criarUsuarioRequest.setLogin("loginJohn");
            criarUsuarioRequest.setSenha("senhaJohn");
            criarUsuarioRequest.setEndereco(new ArrayList<>());
            criarUsuarioRequest.setTipoUsuario(TipoUsuario.DONO_RESTAURANTE);

            when(criarUsuario.criar(criarUsuarioRequest)).thenThrow(new DataIntegrityViolationException("EMAIL já cadastrado"));

            mockMvc.perform(post("/usuario")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(asJsonString(criarUsuarioRequest)))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.error").value("Violação de integridade"))
                    .andExpect(jsonPath("$.listaErrosValidacao[0].mensagemErroValidacao")
                            .value("Já existe um usuário cadastrado com este e-mail."));


            // Then
            verify(criarUsuario, times(1)).criar(criarUsuarioRequest);
        }
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
