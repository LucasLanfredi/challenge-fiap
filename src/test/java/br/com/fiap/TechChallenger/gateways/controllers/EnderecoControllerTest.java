package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.gateways.controller.EnderecoController;
import br.com.fiap.TechChallenger.handlers.ControllerExceptionHandler;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.CriarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.DeletarEndereco;
import br.com.fiap.TechChallenger.usecases.endereco.EditarEndereco;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EnderecoControllerTest {

    @Mock
    private CriarEndereco criarEndereco;

    @Mock
    private EditarEndereco editarEndereco;

    @Mock
    private DeletarEndereco deletarEndereco;

    @Mock
    private BuscarEndereco buscarEndereco;

    private EnderecoController enderecoController;

    MockMvc mockMvc;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        EnderecoController enderecoController = new EnderecoController(criarEndereco, editarEndereco, deletarEndereco, buscarEndereco);
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
    class CriarEnderecos {

        @Test
        void devePermitirCriarEndereco() throws Exception {
            // Arrange
            EnderecoDTO enderecoDTO = gerarEnderecoDTO();

            when(criarEndereco.criarEnderecosByUserId(enderecoDTO, 1L))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(post("/usuario/endereco/userId/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoDTO)))
                    .andExpect(status().isOk());

            verify(criarEndereco, times(1)).criarEnderecosByUserId(enderecoDTO, 1L);
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarEndereco_ConteudoEmBranco() throws Exception {
            // Arrange
            EnderecoDTO enderecoDTO = gerarEnderecoDTO();
            enderecoDTO.setRua("");
            mockMvc.perform(post("/usuario/endereco/userId/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoDTO)))
                    .andExpect(status().isUnprocessableEntity())
                    .andExpect(jsonPath("$.error").value("Dados inválidos"))
                    .andExpect(jsonPath("$.listaErrosValidacao[0].mensagemErroValidacao")
                            .value("A rua é obrigatória."));

            verify(criarEndereco, never()).criarEnderecosByUserId(enderecoDTO, 1L);
        }
    }

    @Nested
    class EditarEnderecoById {
        @Test
        void devePermitirEditarEndereco() throws Exception {
            // Arrange
            Endereco endereco = gerarEndereco();
            EnderecoEditDTO enderecoEditRequest = gerarEnderecoEditDTO(endereco);
            enderecoEditRequest.setEnderecoId(1L);

            when(editarEndereco.editarEnderecosByEnderecoId(enderecoEditRequest))
                    .thenReturn(ResponseEntity.ok().build());

            mockMvc.perform(put("/usuario/endereco")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(enderecoEditRequest)))
                    .andExpect(status().isOk());

            verify(editarEndereco, times(1)).editarEnderecosByEnderecoId(enderecoEditRequest);
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
