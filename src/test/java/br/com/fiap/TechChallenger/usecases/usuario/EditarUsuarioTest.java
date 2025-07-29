package br.com.fiap.TechChallenger.usecases.usuario;

import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioEditDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("teste")
public class EditarUsuarioTest {
    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    private EditarUsuario usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new EditarUsuario(usuarioRepository, autenticacao);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class EditarUsuarioById {

        @Test
        void DevePermitirEditarUsuarioPorId() {
            Usuario usuario = gerarUsuario();

            UsuarioEditDTO usuarioDTO = new UsuarioEditDTO();
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setEmail(usuario.getEmail());

            when(usuarioRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(usuario));
            when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

            var response = usecase.editarUsuarioByUserId(
                    usuarioDTO,
                    usuario.getId()
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(response.getBody().get("mensagem")).isEqualTo("Usuário editado com sucesso!");
            assertThat(response.getBody().get("id")).isEqualTo(usuario.getId());

            verify(usuarioRepository, times(1)).save(any(Usuario.class));
            verify(usuarioRepository, times(1)).findById(anyLong());
        }

        @Test
        void DeveRetornarErroSeUsuarioNaoExistir() {

            when(usuarioRepository.findById(any(Long.class)))
                    .thenReturn(Optional.empty());

            assertThatThrownBy(() -> {
                usecase.editarUsuarioByUserId(null, 1L);
            }).isInstanceOf(RuntimeException.class)
              .hasMessage("Usuário não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(usuarioRepository, never()).save(any());
        }

    }

    @Nested
    class EditarUsuarioLogado {
        @Test
        void devePermitirEditarUsuarioLogado() throws Exception {
            Usuario usuario = gerarUsuario();
            UsuarioEditDTO usuarioDTO = new UsuarioEditDTO();
            usuarioDTO.setNome(usuario.getNome());
            usuarioDTO.setEmail(usuario.getEmail());

            UsuarioLogado usuarioLogado = gerarUsuarioLogado();

            when(autenticacao.getUsuarioLogado(any()))
                    .thenReturn(usuarioLogado);
            when(usuarioRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(usuario));
            when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

            var response = usecase.editar(usuarioDTO, null);
            Map map = (Map) response.getBody();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            assertThat(map.get("mensagem")).isEqualTo("Usuário editado com sucesso!");
            assertThat(map.get("id")).isEqualTo(usuario.getId());

            verify(autenticacao, times(1)).getUsuarioLogado(any());
            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(usuarioRepository, times(1)).save(any(Usuario.class));
        }

    }
}
