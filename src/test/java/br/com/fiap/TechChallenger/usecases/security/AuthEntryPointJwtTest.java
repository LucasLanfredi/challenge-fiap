package br.com.fiap.TechChallenger.usecases.security;

import jakarta.servlet.ServletOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Profile("teste")
public class AuthEntryPointJwtTest {

    private AuthEntryPointJwt usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new AuthEntryPointJwt();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Nested
    class HandleUnauthorizedRequest {
        @Test
        void handleUnauthorizedRequest() throws IOException {
            var request = mock(jakarta.servlet.http.HttpServletRequest.class);
            var response = mock(jakarta.servlet.http.HttpServletResponse.class);
            var outputStream = new ByteArrayOutputStream();
            var servletOutputStream = new ServletOutputStream() {
                @Override
                public void write(int b) {
                    outputStream.write(b);
                }
                @Override
                public boolean isReady() { return true; }
                @Override
                public void setWriteListener(jakarta.servlet.WriteListener writeListener) {}
            };

            when(response.getOutputStream()).thenReturn(servletOutputStream);
            when(request.getServletPath()).thenReturn("/api/teste");

            var authException = new org.springframework.security.core.AuthenticationException("Token inválido") {};

            usecase.commence(request, response, authException);

            verify(response).setContentType(APPLICATION_JSON_VALUE);
            verify(response).setStatus(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);

            String json = outputStream.toString();
            assertThat(json).contains("\"status\":401");
            assertThat(json).contains("\"error\":\"Unauthorized\"");
            assertThat(json).contains("\"message\":\"Token inválido\"");
            assertThat(json).contains("\"path\":\"/api/teste\"");
        }
    }
}
