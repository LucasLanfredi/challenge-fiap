package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.restaurante.BuscarRestaurante;
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
import static br.com.fiap.TechChallenger.helpers.ItensMenuHelper.gerarItemMenu;
import static br.com.fiap.TechChallenger.helpers.ItensMenuHelper.gerarItemMenuDTO;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Profile("teste")
public class CriarItemMenuTest {
    @Mock
    private ItemMenuRepository itemMenuRepository;

    @Mock
    private RestauranteRepository restauranteRepository;

    private CriarItemMenu usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new CriarItemMenu(itemMenuRepository, restauranteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class CriarItemMenuTestCases {
        @Test
        void devePermitirCriarItemMenuComDadosValidos() {

            Usuario usuario = gerarUsuario();
            Endereco endereco = gerarEndereco();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);
            ItemMenuDTO itemMenuDTO = gerarItemMenuDTO(itemMenu);

            when(restauranteRepository.findById(anyLong())).thenReturn(Optional.of(restaurante));
            when(itemMenuRepository.save(any())).thenReturn(itemMenu);

            var response = usecase.criar(itemMenuDTO);

            assertThat(response.getStatusCode())
                    .isNotNull()
                    .isEqualTo(HttpStatus.CREATED);
            assertThat(response.getBody())
                    .isInstanceOf(ItemMenuResponse.class)
                    .extracting(ItemMenuResponse::nomePrato, ItemMenuResponse::descricaoPrato, ItemMenuResponse::preco)
                    .contains(itemMenu.getNomePrato(), itemMenu.getDescricaoPrato(), itemMenu.getPreco())
                    .isNotNull();

            verify(itemMenuRepository, times(1)).save(any());
            verify(restauranteRepository, times(1)).findById(anyLong());
        }

        @Test
        void deveLancarExcecaoQuandoRestauranteNaoEncontrado() {

            ItemMenuDTO itemMenuDTO = gerarItemMenuDTO(gerarItemMenu(null));

            when(restauranteRepository.findById(anyLong())).thenReturn(Optional.empty());

            var response = usecase.criar(itemMenuDTO);

            assertThat(response.getStatusCode())
                    .isNotNull()
                    .isEqualTo(HttpStatus.UNAUTHORIZED);
            assertThat(response.getBody())
                    .isNull();

            verify(itemMenuRepository, never()).save(any());
            verify(restauranteRepository, times(1)).findById(anyLong());
        }
    }
}
