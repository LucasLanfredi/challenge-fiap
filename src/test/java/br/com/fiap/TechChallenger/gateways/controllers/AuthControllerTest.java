package br.com.fiap.TechChallenger.gateways.controllers;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.dto.LoginRequest;
import br.com.fiap.TechChallenger.domains.dto.response.JwtResponse;
import br.com.fiap.TechChallenger.gateways.controller.AuthController;
import br.com.fiap.TechChallenger.handlers.ControllerExceptionHandler;
import br.com.fiap.TechChallenger.usecases.usuario.AutenticacaoUsuario;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
@Profile("teste")
public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AutenticacaoUsuario autenticacaoUsuario;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        AuthController authController = new AuthController(autenticacaoUsuario);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
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
    class LogarUsuario {
        @Test
        void devePermitirLogarUsuario() throws Exception {
            LoginRequest login = new LoginRequest();
            login.setLogin("user");
            login.setSenha("123456");

            JwtResponse jwtResponse = new JwtResponse("Bearer", 1L, "user", "user@email.com", TipoUsuario.DONO_RESTAURANTE);

            when(autenticacaoUsuario.autenticar(login)).thenReturn(ResponseEntity.ok(jwtResponse));

            mockMvc.perform(post("/auth/login")
                    .contentType("application/json")
                    .content(asJsonString(login)))
                    .andExpect(result -> {
                        log.info("Response: {}", result.getResponse().getContentAsString());
                        result.getResponse().setStatus(200);
                    });
            verify(autenticacaoUsuario, times(1)).autenticar(login);
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
