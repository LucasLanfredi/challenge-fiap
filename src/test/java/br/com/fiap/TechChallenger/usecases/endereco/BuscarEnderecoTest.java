package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("teste")
public class BuscarEnderecoTest {

    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private BuscarEndereco usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new BuscarEndereco(enderecoRepository, autenticacao, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarTodosOsEnderecos {
        @Test
        void devePermitirBuscarTodosOsEnderecos() {
            Endereco endereco = gerarEndereco();

            when(enderecoRepository.findAll()).thenReturn(List.of(endereco));

            var enderecosRetornados = usecase.buscarTodosEnderecos();

            assertThat(enderecosRetornados).isNotNull()
                    ;

            assertThat(enderecosRetornados.getBody()).isNotNull()
                    .isNotEmpty()
                    .hasSize(1)
                    .containsExactly(EnderecoResponse.fromEntity(endereco));

            verify(enderecoRepository, times(1)).findAll();
        }
    }

    @Nested
    class BuscarEnderecosPorUsuario {
        @Test
        void devePermitirBuscarEnderecosPorUsuario() {
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuario();
            Long id = 1L;

            when(enderecoRepository.getEnderecosByUsuario(any(Usuario.class))).thenReturn(List.of(endereco));
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));

            var enderecosRetornados = usecase.buscarEnderecosByUserId(id);

            assertThat(enderecosRetornados.getStatusCode()).isNotNull()
                    .isEqualTo(HttpStatus.OK);

            assertThat(enderecosRetornados.getBody()).isNotNull()
                    .hasSize(1)
                    .containsExactly(EnderecoResponse.fromEntity(endereco));

            verify(enderecoRepository, times(1)).getEnderecosByUsuario(any(Usuario.class));
            verify(usuarioRepository, times(1)).findById(anyLong());
        }

        @Test
        void deveRetornarErroQuandoUsuarioNaoEstiverLogado() {
            Endereco endereco = gerarEndereco();
            Long id = 1L;

            when(enderecoRepository.getEnderecosByUsuario(any(Usuario.class))).thenReturn(List.of(endereco));
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.buscarEnderecosByUserId(id))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessage("Usuário não encontrado");
            verify(enderecoRepository, times(0)).getEnderecosByUsuario(any(Usuario.class));
            verify(usuarioRepository, times(1)).findById(anyLong());

        }
    }

    @Nested
    class BuscarEnderecosPorUsuarioLogado {
        @Test
        void devePermitirBuscarEnderecosPorUsuarioLogado() throws AuthException {
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuario();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(new UsuarioLogado("", "", 1L, ""));
            when(enderecoRepository.getEnderecosByUsuario(any(Usuario.class))).thenReturn(List.of(endereco));
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
            var enderecosRetornados = usecase.buscarEnderecos(mock(HttpServletRequest.class));
            assertThat(enderecosRetornados.getStatusCode()).isNotNull()
                    .isEqualTo(HttpStatus.OK);
            assertThat(enderecosRetornados.getBody()).isNotNull()
                    .hasSize(1)
                    .containsExactly(EnderecoResponse.fromEntity(endereco));

            verify(enderecoRepository, times(1)).getEnderecosByUsuario(any(Usuario.class));
            verify(usuarioRepository, times(1)).findById(anyLong());
        }

        @Test
        void deveRetornarErroQuandoUsuarioNaoEstiverLogado() throws AuthException {
            when(autenticacao.getUsuarioLogado(any())).thenThrow(new AuthException("Usuário não autenticado"));

            var enderecosRetornados = usecase.buscarEnderecos(mock(HttpServletRequest.class));

            assertThat(enderecosRetornados.getStatusCode()).isNotNull()
                    .isEqualTo(HttpStatus.UNAUTHORIZED);
            assertThat(enderecosRetornados.getBody()).isNull();
            verify(enderecoRepository, times(0)).getEnderecosByUsuario(any(Usuario.class));
            verify(usuarioRepository, times(0)).findById(anyLong());
        }
    }

}
