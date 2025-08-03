package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.response.RestauranteResponse;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
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
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestauranteTipo;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuarioDonoDeRestaurante;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@Profile("teste")
public class BuscarRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    private BuscarRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new BuscarRestaurante(restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarRestauranteById {

        @Test
        void deveRetornarRestauranteQuandoIdExistir() {
            Long id = 1L;
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            List<ItemMenu> itemMenuList = new ArrayList<>();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, itemMenuList);
            when(restauranteRepository.findById(id))
                    .thenReturn(Optional.of(restaurante));

            var resultado = usecase.buscaPorId(id);

            assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(resultado.getBody())
                    .isEqualTo(RestauranteResponse.converte(restaurante));

            verify(restauranteRepository, times(1)).findById(id);
        }

        @Test
        void deveRetornarNullQuandoIdNaoExistir() {
            // Arrange
            Long id = 2L;
            when(restauranteRepository.findById(id))
                    .thenReturn(java.util.Optional.empty());

            assertThatThrownBy(() -> usecase.buscaPorId(id))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Restaurante não encontrado");

            verify(restauranteRepository, times(1)).findById(id);
        }
    }

    @Nested
    class BuscaRestauranteLogado {
        @Test
        void deveRetornarTodosRestaurantes() {
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            List<ItemMenu> itemMenuList = new ArrayList<>();
            Restaurante restaurante1 = gerarRestauranteTipo(endereco, usuario, "Italiana", itemMenuList);
            Restaurante restaurante2 = gerarRestauranteTipo(endereco, usuario, "Japonesa", itemMenuList);

            when(restauranteRepository.findAll())
                    .thenReturn(List.of(restaurante1, restaurante2));

            var resultado = usecase.execute();

            assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(resultado.getBody())
                    .hasSize(2)
                    .extracting("nome", "tipoDeCozinha")
                    .containsExactlyInAnyOrder(
                        tuple("Restaurante Teste", "Italiana"),
                        tuple("Restaurante Teste", "Japonesa")
                    );

            verify(restauranteRepository, times(1)).findAll();
        }
    }
}
