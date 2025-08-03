package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.UserDetailsImpl;
import br.com.fiap.TechChallenger.domains.dto.LoginRequest;
import br.com.fiap.TechChallenger.domains.dto.response.JwtResponse;
import br.com.fiap.TechChallenger.usecases.security.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Profile("teste")
public class AutenticacaoUsuarioTest {
    @Mock
    private AuthenticationManager manager;

    @Mock
    private JwtUtils jwtUtils;

    private AutenticacaoUsuario usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new AutenticacaoUsuario(manager, jwtUtils);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class AutenticarUsuario {

        @Test
        void devePermitirAutenticarUsuario() {
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setLogin("admin");
            loginRequest.setSenha("admin123");

            Authentication authentication = mock(Authentication.class);
            UserDetailsImpl userDetails = mock(UserDetailsImpl.class);

            when(manager.authenticate(any())).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn(userDetails);
            when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwt-token");
            when(userDetails.getId()).thenReturn(1L);
            when(userDetails.getUsername()).thenReturn("admin");
            when(userDetails.getEmail()).thenReturn("admin@email.com");
            when(userDetails.getTipoUsuario()).thenReturn(TipoUsuario.DONO_RESTAURANTE);

            // Act
            ResponseEntity<JwtResponse> response = usecase.autenticar(loginRequest);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody())
                    .isNotNull()
                    .extracting(
                            JwtResponse::type,
                            JwtResponse::id,
                            JwtResponse::username,
                            JwtResponse::email,
                            JwtResponse::tipoUsuario
                    )
                    .containsExactly("Bearer", 1L, "admin", "admin@email.com", TipoUsuario.DONO_RESTAURANTE);

        }
    }
}
