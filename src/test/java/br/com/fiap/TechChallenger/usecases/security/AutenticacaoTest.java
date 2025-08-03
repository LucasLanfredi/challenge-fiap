package br.com.fiap.TechChallenger.usecases.security;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.usuario.AutenticacaoUsuario;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Profile("teste")
public class AutenticacaoTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtUtils jwtUtils;

    private Autenticacao usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new Autenticacao(jwtUtils, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class GetUsuarioLogado {

        @Test
        void devePermitirObterUsuarioLogado() throws AuthException {
            String token = "valid.jwt.token";
            String username = "usuarioTeste";
            Usuario usuario = gerarUsuario(1L);

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
            when(jwtUtils.validateJwtToken(token)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
            when(usuarioRepository.findByLogin(username)).thenReturn(Optional.of(usuario));

            UsuarioLogado usuarioLogado = usecase.getUsuarioLogado(request);

            assertThat(usuarioLogado.getId()).isEqualTo(usuario.getId());
            assertThat(usuarioLogado.getNome()).isEqualTo(usuario.getNome());
            assertThat(usuarioLogado.getEmail()).isEqualTo(usuario.getEmail());
            assertThat(usuarioLogado.getPerfil()).isEqualTo(usuario.getTipoUsuario().toString());
        }

        @Test
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            String token = "valid.jwt.token";
            String username = "usuarioInexistente";

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
            when(jwtUtils.validateJwtToken(token)).thenReturn(false);
            when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
            when(usuarioRepository.findByLogin(username)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.getUsuarioLogado(request))
                    .isInstanceOf(AuthException.class)
                    .hasMessage("Nenhum usuário logado encontrado.");
        }

        @Test
        void deveLancarExcecaoQuandoTokenInvalido() {
            String token = "valid.jwt.token";
            String username = "usuarioInexistente";

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("Authorization")).thenReturn("");
            when(jwtUtils.validateJwtToken(token)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
            when(usuarioRepository.findByLogin(username)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.getUsuarioLogado(request))
                    .isInstanceOf(AuthException.class)
                    .hasMessage("Nenhum usuário logado encontrado.");
        }

        @Test
        void deveLancarExcecaoQuandoHeaderNull() {
            String token = "valid.jwt.token";
            String username = "usuarioInexistente";

            HttpServletRequest request = mock(HttpServletRequest.class);
            when(request.getHeader("Authorization")).thenReturn(null);
            when(jwtUtils.validateJwtToken(token)).thenReturn(true);
            when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
            when(usuarioRepository.findByLogin(username)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.getUsuarioLogado(request))
                    .isInstanceOf(AuthException.class)
                    .hasMessage("Nenhum usuário logado encontrado.");
        }
    }


}
