package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
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
import br.com.fiap.TechChallenger.usecases.exception.TipoUsuarioInvalidoException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestauranteDto;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
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

    @Mock
    private UsuarioRepository usuarioRepository;

    private CadastrarRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new CadastrarRestaurante(autenticacao, restauranteRepository, usuarioRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CadastrarRestauranteApi {

        @Test
        void devePermitirCadastrarRestaurante() throws TipoUsuarioInvalidoException {
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuario();
            List<ItemMenu> itemMenus = new ArrayList<>();
            Restaurante restaurante = gerarRestaurante(endereco, usuario, itemMenus);
            RestauranteDto restauranteDto = gerarRestauranteDto();


            when(restauranteRepository.save(any(Restaurante.class))).thenReturn(restaurante);

            var response = usecase.cadastrarRestaurante(
                    restauranteDto
            );

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            assertThat(response.getBody()).isEqualTo(1L);

        }

        @Test
        void deveLancarExcecaoQuandoUsuarioETipoCliente() throws TipoUsuarioInvalidoException {
            Usuario usuarioDTO = gerarUsuario();
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
            List<ItemMenu> itensMenu = new ArrayList<>();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            Restaurante restaurante = gerarRestaurante(endereco, usuario, itensMenu);
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
            Usuario usuarioDTO = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuarioDTO);
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
            Usuario usuarioDTO = gerarUsuario();
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
