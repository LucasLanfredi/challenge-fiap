package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.*;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
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
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.*;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.*;
import static br.com.fiap.TechChallenger.helpers.UsuarioLogadoHelper.gerarUsuarioLogado;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("teste")
public class EditarRestauranteTest {
    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private Autenticacao autenticacao;

    @Mock
    private UsuarioRepository usuarioRepository;

    private EditarRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new EditarRestaurante(autenticacao, restauranteRepository, usuarioRepository, new ModelMapper());
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class EditarRestauranteApi {
        @Test
        void deveEditarRestauranteComSucesso() throws AuthException {

            var request = mock(HttpServletRequest.class);
            Long id = 1L;
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            List<ItemMenu> itensMenu = new ArrayList<>();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            var restauranteExistente = gerarRestaurante(endereco, usuario, itensMenu);
            var restauranteAtualizado = gerarRestaurante(endereco, usuario, itensMenu);
            RestauranteRequestEditDto restauranteDto = gerarRestauranteRequestEditDto(usuario);

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(restauranteExistente));
            when(restauranteRepository.save(any()))
                    .thenReturn(restauranteAtualizado);
            when(autenticacao.getUsuarioLogado(request))
                    .thenReturn(usuarioLogado);

            ResponseEntity<RestauranteResponse> response =
                    (ResponseEntity<RestauranteResponse>) usecase.execute(restauranteDto, request, id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assertThat(response.getBody())
                    .isNotNull()
                    .extracting(RestauranteResponse::tipoDeCozinha)
                    .isEqualTo("Indiana");

            verify(restauranteRepository, times(1)).save(any());
            verify(restauranteRepository, times(1)).findById(id);
            verify(autenticacao, times(1)).getUsuarioLogado(request);
        }

        @Test
        void deveEditarRestauranteComSucessoECamposVazio() throws AuthException {

            var request = mock(HttpServletRequest.class);
            Long id = 1L;
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            List<ItemMenu> itensMenu = new ArrayList<>();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            var restauranteExistente = gerarRestaurante(endereco, usuario, itensMenu);
            RestauranteRequestEditDto restauranteDto = gerarRestauranteRequestEditDto(usuario);

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(restauranteExistente));
            when(restauranteRepository.save(any()))
                    .thenReturn(restauranteExistente);
            when(autenticacao.getUsuarioLogado(request))
                    .thenReturn(usuarioLogado);

            ResponseEntity<RestauranteResponse> response =
                    (ResponseEntity<RestauranteResponse>) usecase.execute(restauranteDto, request, id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assertThat(response.getBody())
                    .isNotNull()
                    .extracting(RestauranteResponse::tipoDeCozinha)
                    .isEqualTo(null);

            verify(restauranteRepository, times(1)).save(any());
            verify(restauranteRepository, times(1)).findById(id);
            verify(autenticacao, times(1)).getUsuarioLogado(request);
        }

        @Test
        void deveLancarExcecaoQuandoRestauranteNaoEncontrado() throws AuthException {
            var request = mock(HttpServletRequest.class);
            Long id = 1L;
            RestauranteRequestEditDto restauranteDto = gerarRestauranteRequestEditDto();

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.empty());

            ResponseEntity<?> response = usecase.execute(restauranteDto, request, id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Restaurante não encontrado");
            verify(restauranteRepository, times(1)).findById(id);
            verify(restauranteRepository, never()).save(any());
            verify(autenticacao, times(1)).getUsuarioLogado(request);
        }

        @Test
        void deveLancarExcecaoQuandoTipoUsuarioInvalido() throws AuthException {
            var request = mock(HttpServletRequest.class);
            Long id = 1L;
            Usuario usuario = gerarUsuario();
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            RestauranteRequestEditDto restauranteDto = gerarRestauranteRequestEditDto(usuario);
            List<ItemMenu> itensMenu = new ArrayList<>();

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(gerarRestaurante(gerarEndereco(), usuario, itensMenu)));
            when(autenticacao.getUsuarioLogado(request))
                    .thenReturn(usuarioLogado);

            ResponseEntity<?> response = usecase.execute(restauranteDto, request, id);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(response.getBody()).isEqualTo("Tipo de usuário inválido para alteração de cadastro");
            verify(restauranteRepository, times(1)).findById(id);
            verify(restauranteRepository, never()).save(any());
            verify(autenticacao, times(1)).getUsuarioLogado(request);
        }
    }

}
