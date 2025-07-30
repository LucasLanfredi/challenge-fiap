package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.dto.TrocaSenhaDto;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.gateways.controller.UsuarioController;
import br.com.fiap.TechChallenger.gateways.controller.UsuarioLogadoController;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("teste")
public class UsuarioLogadoControllerTest {

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
        UsuarioLogadoController usuarioController = new UsuarioLogadoController(
                editarUsuario,
                deletarUsuario,
                buscarUsuario,
                trocarSenha);
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
    class BuscarUsuarioApi {
        @Test
        void devePermitirBuscarUsuarioLogado() throws Exception {
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

            when(buscarUsuario.buscar(any())).thenReturn(ResponseEntity.ok(usuarioResponse));

            // When
            mockMvc.perform(get("/usuarioLogado")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());

            // Then
            verify(buscarUsuario, times(1)).buscar(any());
        }
    }

    @Nested
    class DeletarUsuarioApi {
        @Test
        void devePermitirDeletarUsuarioLogado() throws Exception {
            // Given
            Long userId = 1L;
            // When
            mockMvc.perform(delete("/usuarioLogado", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isOk());

            // Then
            verify(deletarUsuario, times(1)).deletar(any());
        }
    }

    @Nested
    class TrocarSenhaApi {
        @Test
        void devePermitirTrocarSenha() throws Exception {
            // Given
            TrocaSenhaDto trocaSenhaDto = new TrocaSenhaDto();
            trocaSenhaDto.setLogin("john");
            trocaSenhaDto.setSenhaAtual("123456789");
            trocaSenhaDto.setNovaSenha("12345678911");

            // When
            mockMvc.perform(put("/usuarioLogado/senha")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8")
                            .content(asJsonString(trocaSenhaDto)))
                    .andExpect(status().isOk());

            // Then
            verify(trocarSenha, times(1)).execute(any());
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
