package br.com.fiap.TechChallenger.usecases.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Profile("teste")
public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    private AuthTokenFilter usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class DoFilterInternal {
        @Test
        void devePermitirFiltrarTokenDeAutenticacao() throws ServletException, IOException {
            String token = "valid.jwt.token";
            String username = "usuario";
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader("Authorization", "Bearer " + token);
            MockHttpServletResponse response = new MockHttpServletResponse();
            FilterChain filterChain = mock(FilterChain.class);

            UserDetails userDetails = mock(UserDetails.class);
            when(jwtUtils.validateJwtToken(token)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
            when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
            when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());

            usecase.doFilterInternal(request, response, filterChain);

            assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
            assertThat(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).isEqualTo(userDetails);
            verify(filterChain, times(1)).doFilter(request, response);

        }

        @Test
        void deveLancarExcecaoQuandoTokenForInvalido() throws ServletException, IOException {
            String token = "valid.jwt.token";
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader("Authorization", "Bearer " + token);
            MockHttpServletResponse response = new MockHttpServletResponse();
            FilterChain filterChain = mock(FilterChain.class);

            when(jwtUtils.validateJwtToken(token)).thenThrow(new RuntimeException("Token inválido"));

            usecase.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
        }

        @Test
        void deveLancarExcecaoQuandoTokenForVazio() throws ServletException, IOException {
            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader("Authorization", "");
            MockHttpServletResponse response = new MockHttpServletResponse();
            FilterChain filterChain = mock(FilterChain.class);

            usecase.doFilterInternal(request, response, filterChain);

            verify(filterChain, times(1)).doFilter(request, response);
        }

    }
}
