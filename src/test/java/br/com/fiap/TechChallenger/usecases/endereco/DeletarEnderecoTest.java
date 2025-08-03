package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
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
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@Profile("teste")
public class DeletarEnderecoTest {

    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    private DeletarEndereco usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new DeletarEndereco(autenticacao, enderecoRepository, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class DeletarEnderecoById {

        @Test
        void devePermitirDeletarEndereco() {
            // Given
            Long enderecoId = 1L;

            // When
            var enderecoDeletado = usecase.deletarEnderecoById(enderecoId);

            // Then
            assertThat(enderecoDeletado.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(enderecoRepository, times(1)).deleteById(enderecoId);
        }

    }

    @Nested
    class DeletarEnderecos {

        @Test
        void devePermitirDeletarEnderecos() throws AuthException {
            // Given
            Long enderecoId = 1L;


            Usuario usuario = gerarUsuario();
            Endereco endereco = gerarEndereco(usuario);
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));


            // When
            var response = usecase.deletarEnderecos(enderecoId, null);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(enderecoRepository, times(1)).deleteById(enderecoId);
            verify(autenticacao, times(1)).getUsuarioLogado(null);
        }

        @Test
        void deveRetornarErroQuandoUsuarioNaoEncontrado() throws AuthException {
            Long enderecoId = 1L;
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.empty());

            var response = usecase.deletarEnderecos(enderecoId, null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Usuário não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).findById(anyLong());
            verify(enderecoRepository, never()).deleteById(anyLong());
        }

        @Test
        void deveRetornarErroQuandoEndercoNaoEncontrado() throws AuthException {
            Long enderecoId = 1L;
            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuario));
            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

            var response = usecase.deletarEnderecos(enderecoId, null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Endereço não encontrado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).deleteById(anyLong());
        }

        @Test
        void deveRetornarErroQuandoEnderecoNaoPertenceAoUsuarioLogado() throws AuthException {
            Long enderecoId = 1L;
            Long usuarioId = 2L;
            Usuario usuario = gerarUsuario();
            Usuario usuarioDiferente = gerarUsuario(usuarioId);
            Endereco endereco = gerarEndereco(usuario);
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioDiferente));
            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));

            var response = usecase.deletarEnderecos(enderecoId, null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Este endereço não pertence ao usuário logado");

            verify(usuarioRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).deleteById(anyLong());
        }
    }

}
