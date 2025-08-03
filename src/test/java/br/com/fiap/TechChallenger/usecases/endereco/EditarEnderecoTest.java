package br.com.fiap.TechChallenger.usecases.endereco;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.EnderecoEditDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.EnderecoResponse;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
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
import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEnderecoEditDTO;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Profile("teste")
public class EditarEnderecoTest {
    @Mock
    private Autenticacao autenticacao;

    @Mock
    private EnderecoRepository enderecoRepository;

    private EditarEndereco usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new EditarEndereco(autenticacao, enderecoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class EditarEnderecoById {

        @Test
        void devePermitirEditarEndereco() {
            Endereco endereco = gerarEndereco();
            EnderecoEditDTO editarEnderecoRequest = gerarEnderecoEditDTO();

            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));
            when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);

            var response = usecase.editarEnderecosByEnderecoId(editarEnderecoRequest);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody())
                    .isNotNull()
                    .isInstanceOf(EnderecoResponse.class)
                    .isEqualTo(EnderecoResponse.fromEntity(endereco));

            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, times(1)).save(any(Endereco.class));
        }

        @Test
        void deveRetornarErroSeEnderecoNaoExistir() {
            EnderecoEditDTO editarEnderecoRequest = gerarEnderecoEditDTO();

            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.editarEnderecosByEnderecoId(editarEnderecoRequest))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("Endereço não encontrado");

            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).save(any(Endereco.class));
        }
    }

    @Nested
    class EditarEnderecos {
        @Test
        void devePermitirEditarEnderecos() throws AuthException {

            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();
            Endereco endereco = gerarEndereco(usuario);
            EnderecoEditDTO editarEnderecoRequest = gerarEnderecoEditDTO(endereco);

            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));
            when(enderecoRepository.save(any(Endereco.class))).thenReturn(endereco);
            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);

            var response = usecase.editarEnderecos(editarEnderecoRequest, null);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody())
                    .isNotNull()
                    .isInstanceOf(EnderecoResponse.class)
                    .isEqualTo(EnderecoResponse.fromEntity(endereco));

            verify(enderecoRepository, times(2)).findById(anyLong());
            verify(enderecoRepository, times(1)).save(any(Endereco.class));
        }

        @Test
        void deveRetornarErroSeUsuarioDiferente() throws AuthException {
            Usuario usuario = gerarUsuario(2L);
            UsuarioLogado usuarioLogado = gerarUsuarioLogado();
            Endereco endereco = gerarEndereco(usuario);
            EnderecoEditDTO editarEnderecoRequest = gerarEnderecoEditDTO(endereco);

            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.of(endereco));
            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);

            var response = usecase.editarEnderecos(editarEnderecoRequest, null);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Este endereço não pertence ao usuário logado");

            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).save(any(Endereco.class));
        }

        @Test
        void deveRetornarErroSeEnderecoNaoExistir() {
            EnderecoEditDTO editarEnderecoRequest = gerarEnderecoEditDTO();

            when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

            var response = usecase.editarEnderecos(editarEnderecoRequest, null);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Endereço não encontrado");

            verify(enderecoRepository, times(1)).findById(anyLong());
            verify(enderecoRepository, never()).save(any(Endereco.class));
        }
    }

}
