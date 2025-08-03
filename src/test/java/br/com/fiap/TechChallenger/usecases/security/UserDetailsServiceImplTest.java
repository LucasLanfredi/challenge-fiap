package br.com.fiap.TechChallenger.usecases.security;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
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
import static org.mockito.Mockito.*;

@Profile("teste")
public class UserDetailsServiceImplTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    private UserDetailsServiceImpl usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new UserDetailsServiceImpl(usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class LoadUserByUsername {
        @Test
        void devePermitirCarregarUsuarioLogado() {
            Usuario usuario = gerarUsuario();

            when(usuarioRepository.findByLogin(usuario.getEmail())).thenReturn(Optional.of(usuario));

            var userDetails = usecase.loadUserByUsername(usuario.getEmail());

            assertThat(userDetails).isNotNull();
            assertThat(userDetails.getUsername()).isEqualTo(usuario.getLogin());
            assertThat(userDetails.getPassword()).isEqualTo(usuario.getSenha());

            verify(usuarioRepository, times(1)).findByLogin(usuario.getEmail());
        }

        @Test
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.loadUserByUsername("usuarioInexistente"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Usuário não encontrado com login: usuarioInexistente");

            verify(usuarioRepository, times(1)).findByLogin("usuarioInexistente");
        }
    }
}
