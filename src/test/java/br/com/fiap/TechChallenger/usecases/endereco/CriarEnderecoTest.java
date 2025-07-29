package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
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

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEnderecoDTO;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("teste")
public class CriarEnderecoTest {

    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private CriarEndereco usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new CriarEndereco(usuarioRepository, autenticacao, enderecoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    public class CriarEnderecosByUserId {

        @Test
        void devePermitirCriarEndereco() throws AuthException {
            // Given
            var endereco = gerarEndereco();
            var usuario = gerarUsuario();

            var enderecoDTO = gerarEnderecoDTO();

            when(usuarioRepository.findById(anyLong()))
                    .thenReturn(Optional.of(usuario));
            when(enderecoRepository.save(any())).thenReturn(endereco);

            var usuarioCriado = usecase.criarEnderecosByUserId(enderecoDTO, 1L);

            assertThat(usuarioCriado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(usuarioCriado.getBody()).isNotNull().isInstanceOf(EnderecoResponse.class);
            verify(usuarioRepository, times(1)).findById(1L);
            verify(enderecoRepository, times(1)).save(any(Endereco.class));
        }

        @Test
        void deveRetornarErroQuandoUsuarioNaoEstiverLogado() {
            var enderecoDTO = gerarEnderecoDTO();

            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.criarEnderecosByUserId(enderecoDTO, 1L))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Usuário não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).save(any(Endereco.class));
        }

    }

    @Nested
    class CriarEnderecos {
        @Test
        void devePermitirCriarEnderecoComUsuarioLogado() throws AuthException {
            // Given
            var endereco = gerarEndereco();
            var usuario = gerarUsuario();
            var usuarioLogado = gerarUsuarioLogado();

            var enderecoDTO = gerarEnderecoDTO();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong()))
                    .thenReturn(Optional.of(usuario));
            when(enderecoRepository.save(any())).thenReturn(endereco);

            var response = usecase.criarEnderecos(enderecoDTO, null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isNotNull().isInstanceOf(EnderecoResponse.class);
            verify(usuarioRepository, times(1)).findById(usuarioLogado.getId());
            verify(enderecoRepository, times(1)).save(any(Endereco.class));
        }

        @Test
        void deveRetornarErroQuandoUsuarioNaoEncontrado() throws AuthException {
            // Given
            var enderecoDTO = gerarEnderecoDTO();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(gerarUsuarioLogado());
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.criarEnderecos(enderecoDTO, null))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Usuário não encontrado");

            verify(enderecoRepository, never()).save(any(Endereco.class));
        }
    }




}
