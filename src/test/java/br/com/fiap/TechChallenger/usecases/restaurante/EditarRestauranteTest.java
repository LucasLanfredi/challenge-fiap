package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.HorariosDeFuncionamentoDTO;
import br.com.fiap.TechChallenger.domains.dto.RestauranteDto;
import br.com.fiap.TechChallenger.domains.dto.UsuarioDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
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
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestauranteDto;
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

    private EditarRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new EditarRestaurante(autenticacao, restauranteRepository);
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
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            var restauranteExistente = gerarRestaurante(endereco, usuario);
            var restauranteAtualizado = gerarRestaurante(endereco, usuario, "Indiana");
            RestauranteDto restauranteDto = gerarRestauranteDto(restauranteAtualizado);

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(restauranteExistente));
            when(restauranteRepository.save(any()))
                    .thenReturn(restauranteAtualizado);
            when(autenticacao.getUsuarioLogado(request))
                    .thenReturn(usuarioLogado);

            ResponseEntity<RestauranteResponse> response = (ResponseEntity<RestauranteResponse>) usecase.execute(restauranteDto, request, id);

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
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            UsuarioDTO usuarioDTO = gerarUsuarioDTO(usuario);
            var restauranteExistente = gerarRestaurante(endereco, usuario);
            RestauranteDto restauranteDto = new RestauranteDto();
            restauranteDto.setDonoDoRestaurante(usuarioDTO);
            restauranteDto.setHorariosDeFuncionamentoDTO(new HorariosDeFuncionamentoDTO());

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(restauranteExistente));
            when(restauranteRepository.save(any()))
                    .thenReturn(restauranteExistente);
            when(autenticacao.getUsuarioLogado(request))
                    .thenReturn(usuarioLogado);

            ResponseEntity<RestauranteResponse> response = (ResponseEntity<RestauranteResponse>) usecase.execute(restauranteDto, request, id);

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
            RestauranteDto restauranteDto = gerarRestauranteDto();

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
            UsuarioDTO usuarioDTO = gerarUsuarioDTO(usuario);
            UsuarioLogado usuarioLogado = gerarUsuarioLogado(usuario);
            RestauranteDto restauranteDto = gerarRestauranteDto(usuarioDTO);

            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(gerarRestaurante(gerarEndereco(), usuario)));
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
