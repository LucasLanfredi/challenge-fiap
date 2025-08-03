package br.com.fiap.TechChallenger.usecases.restaurante;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
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
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuarioDonoDeRestaurante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("teste")
public class ExcluirRestauranteTest {

    @Mock
    private RestauranteRepository restauranteRepository;

    @Mock
    private Autenticacao autenticacao;

    private ExcluirRestaurante usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new ExcluirRestaurante(autenticacao, restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class DeletarRestauranteById {

        @Test
        void deveDeletarRestauranteQuandoIdExistir() {
            Long id = 1L;
            Endereco endereco = gerarEndereco();
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            List<ItemMenu> itemMenuList = new ArrayList<>();
            Restaurante restaurante = gerarRestaurante(endereco, usuario, itemMenuList);

            when(restauranteRepository.findById(id))
                .thenReturn(Optional.of(restaurante));

            var resultado = usecase.execute(id, null);

            assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
            verify(restauranteRepository, times(1)).findById(id);
            verify(restauranteRepository, times(1)).deleteById(id);
        }

        @Test
        void deveLancarExcecaoQuandoIdNaoExistir() {
            Long id = 2L;
            when(restauranteRepository.findById(id))
                .thenReturn(Optional.empty());

            var resultado = usecase.execute(id, null);
            assertThat(resultado.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(resultado.getBody()).isEqualTo("Restaurante não encontrado");

            verify(restauranteRepository, times(1)).findById(id);
            verify(restauranteRepository, never()).deleteById(anyLong());
        }
    }
}