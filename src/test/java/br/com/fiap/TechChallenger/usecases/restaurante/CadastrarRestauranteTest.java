package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.EnderecoDTO;
import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.gateways.repository.EnderecoRepository;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.gateways.repository.UsuarioRepository;
import br.com.fiap.TechChallenger.helpers.RestauranteHelper;
import br.com.fiap.TechChallenger.usecases.endereco.BuscarEndereco;
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

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestauranteDto;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuarioDTO;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Profile("profile")
public class CadastrarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private Autenticacao autenticacao;

    private CadastrarRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new CadastrarRestaurante(autenticacao, restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarRestauranteApi {

        @Test
        void devePermitirCadastrarRestaurante() {
            // Implementar o teste para cadastrar restaurante
            // Exemplo: usecase.cadastrarRestaurante(...);
            // Verificar se o restaurante foi salvo no repositório
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuario();
            Restaurante restaurante = gerarRestaurante(endereco, usuario);
            RestauranteDto restauranteDto = gerarRestauranteDto();


            when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

            var response = usecase.cadastrarRestaurante(
                    restauranteDto
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            assertThat(response.getBody()).isEqualTo(1L);

        }

        @Test
        void deveLancarExcecaoQuandoUsuarioETipoCliente() {
            UsuarioDTO usuarioDTO = gerarUsuarioDTO();
            RestauranteDto restauranteDto = gerarRestauranteDto(usuarioDTO);

            var response = usecase.cadastrarRestaurante(
                    restauranteDto
            );
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Tipo de usuário inválido para cadastro de restaurante");
        }

    }

    @Nested
    class CadastrarRestauranteLogado {
        @Test
        void devePermitirCadastrarRestauranteLogado() throws AuthException {
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            Restaurante restaurante = gerarRestaurante(endereco, usuario);
            RestauranteDto restauranteDto = gerarRestauranteDto();

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);
            when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

            var response = usecase.execute(
                    restauranteDto,
                    null
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody()).isEqualTo(1L);
        }

        @Test
        void deveLancarExcecaoQuandoUsuarioETipoClienteLogado() throws AuthException {
            UsuarioDTO usuarioDTO = gerarUsuarioDTO();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(gerarUsuario(usuarioDTO));
            RestauranteDto restauranteDto = gerarRestauranteDto(usuarioDTO);

            when(autenticacao.getUsuarioLogado(any())).thenReturn(usuarioLogado);

            var response = usecase.execute(
                    restauranteDto,
                    null
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Tipo de usuário inválido para cadastro de restaurante");
        }

        @Test
        void deveLancarExcecaoQuandoNaoConseguirObterUsuario() throws AuthException {
            UsuarioDTO usuarioDTO = gerarUsuarioDTO();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(gerarUsuario(usuarioDTO));
            RestauranteDto restauranteDto = gerarRestauranteDto(usuarioDTO);

            when(autenticacao.getUsuarioLogado(any())).thenThrow(new RuntimeException("Erro ao obter usuário logado"));

            var response = usecase.execute(
                    restauranteDto,
                    null
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Erro ao obter usuário logado");
        }
    }

}
