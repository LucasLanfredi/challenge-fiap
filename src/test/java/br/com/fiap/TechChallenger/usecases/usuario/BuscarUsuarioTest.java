package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.UsuarioResponse;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.security.auth.message.AuthException;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Profile("teste")
public class BuscarUsuarioTest {
    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    private BuscarUsuario usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new BuscarUsuario(autenticacao, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarUsuarioById {
        @Test
        void devePermitirBuscarUsuarioPorId() {
            Usuario usuario = gerarUsuario();
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

            var response = usecase.getUsuarioResponseById(usuario.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull()
                    .isEqualTo(UsuarioResponse.fromEntity(usuario));
            verify(usuarioRepository, times(1)).findById(anyLong());
        }

        @Test
        void deveRetornarErroAoBuscarUsuarioInexistente() {

            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.getUsuarioResponseById(999L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Usuário não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
        }

    }

    @Nested
    class BuscarUsuarioLogado {
        @Test
        void devePermitirBuscarUsuarioLogado() throws AuthException {
            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();
            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

            var response = usecase.buscar(null);

            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            AssertionsForClassTypes.assertThat(response.getBody()).isNotNull()
                    .isEqualTo(UsuarioResponse.fromEntity(usuario));
            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(autenticacao, times(1)).getUsuarioLogado(any());
        }

        @Test
        void deveRetornarErroAoBuscarUsuarioLogadoInexistente() throws AuthException {
            when(autenticacao.getUsuarioLogado(any())).thenThrow(new AuthException("Usuário não autenticado"));

            var response = usecase.buscar(null);

            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

            verify(autenticacao, times(1)).getUsuarioLogado(any());
            verify(usuarioRepository, never()).findById(anyLong());
        }
    }

    @Nested
    class BuscarTodosUsuarios {

        @Test
        void devePermitirBuscarTodosUsuarios() {
            Usuario usuario1 = gerarUsuario();
            Usuario usuario2 = gerarUsuario();
            when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

            ResponseEntity<List<UsuarioResponse>> response = usecase.buscarTodosUsuarios();

            AssertionsForClassTypes.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull()
                    .hasSize(2)
                    .containsExactly(UsuarioResponse.fromEntity(usuario1), UsuarioResponse.fromEntity(usuario2));
            verify(usuarioRepository, times(1)).findAll();
        }


    }




}
