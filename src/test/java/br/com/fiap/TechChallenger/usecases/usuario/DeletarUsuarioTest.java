package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.security.auth.message.AuthException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Profile("teste")
public class DeletarUsuarioTest {

    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    private DeletarUsuario usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new DeletarUsuario(usuarioRepository, autenticacao);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Nested
    class DeletarUsuarioById {

        @Test
        void devePermitirDeletarUsuarioPorId() {
            Usuario usuario = gerarUsuario();
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

            var response = usecase.deleteUsuarioById(usuario.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo("Usuário deletado com sucesso!");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(usuarioRepository, times(1)).delete(any(Usuario.class));

        }

        @Test
        void deveRetornarErroAoDeletarUsuarioInexistente() {
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.deleteUsuarioById(1L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Usuário não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(usuarioRepository, never()).delete(any(Usuario.class));
        }
    }

    @Nested
    class DeletarUsuarioLogado {

        @Test
        void devePermitirDeletarUsuarioLogado() throws AuthException {
            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();
            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

            var response = usecase.deletar(null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo("Usuário deletado com sucesso!");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(usuarioRepository, times(1)).delete(any(Usuario.class));
        }

    }


}
