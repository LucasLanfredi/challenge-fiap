package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.gateways.controller.EnderecoController;
import br.com.fiap.TechChallenger.gateways.controller.EnderecoLogadoController;
import br.com.fiap.TechChallenger.handlers.ControllerExceptionHandler;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.CriarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.DeletarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.EditarEndereco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.connector.Request;
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

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Profile("teste")
public class EnderecoLogadoControllerTest {
    @Mock
    private CriarEndereco criarEndereco;

    @Mock
    private EditarEndereco editarEndereco;

    @Mock
    private DeletarEndereco deletarEndereco;

    @Mock
    private BuscarEndereco buscarEndereco;

    MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        EnderecoLogadoController enderecoController = new EnderecoLogadoController(criarEndereco, editarEndereco, deletarEndereco, buscarEndereco);
        mockMvc = MockMvcBuilders.standaloneSetup(enderecoController)
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
    public class CriarEnderecoApi {
        @Test
        void devePermitirCriarEndereco() throws Exception {
            EnderecoDTO enderecoDTO = gerarEnderecoDTO();

            MockHttpServletRequest request = new MockHttpServletRequest();

            when(criarEndereco.criarEnderecos(enderecoDTO, request))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(post("/usuarioLogado/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoDTO)))
                    .andExpect(status().isOk());

            verify(criarEndereco, times(1)).criarEnderecos(any(EnderecoDTO.class), any());
        }
    }

    @Nested
    public class EditarEnderecoApi {
        @Test
        void devePermitirEditarEndereco() throws Exception {
            var endereco = gerarEndereco();
            var enderecoEditDTO = gerarEnderecoEditDTO(endereco);

            when(editarEndereco.editarEnderecos(any(), any()))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(
                    put("/usuarioLogado/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoEditDTO))
            ).andExpect(status().isOk());

            verify(editarEndereco, times(1)).editarEnderecos(any(), any());
        }
    }

    @Nested
    public class DeletarEnderecoApi {
        @Test
        void devePermitirDeletarEndereco() throws Exception {
            Long enderecoId = 1L;
            MockHttpServletRequest request = new MockHttpServletRequest();

            when(deletarEndereco.deletarEnderecos(enderecoId, request))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(
                    org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/usuarioLogado/endereco/enderecoId/" + enderecoId)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            verify(deletarEndereco, times(1)).deletarEnderecos(anyLong(), any());
        }
    }

    @Nested
    public class BuscarEnderecoApi {
        @Test
        void devePermitirBuscarEnderecos() throws Exception {
            MockHttpServletRequest request = new MockHttpServletRequest();

            when(buscarEndereco.buscarEnderecos(request))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(
                    get("/usuarioLogado/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());

            verify(buscarEndereco, times(1)).buscarEnderecos(any());
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
