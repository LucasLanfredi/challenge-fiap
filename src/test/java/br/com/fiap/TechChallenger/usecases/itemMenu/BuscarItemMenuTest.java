package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.exception.RecursoNaoEncontradoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.ItensMenuHelper.gerarItemMenu;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuario;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@Profile("teste")
public class BuscarItemMenuTest {
    @Mock
    private ItemMenuRepository itemMenuRepository;

    private BuscarItemMenu usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new BuscarItemMenu(itemMenuRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class BuscarTodosItensMenu {
        @Test
        void deveRetornarTodosOsItensMenu() {
            Usuario usuario = gerarUsuario();
            Endereco endereco = gerarEndereco();
            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);

            when(itemMenuRepository.findAll()).thenReturn(List.of(itemMenu));

            var response =  usecase.buscarTodos();

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody())
                    .hasSize(1)
                    .containsExactly(ItemMenuResponse.fromEntity(itemMenu));

            verify(itemMenuRepository, times(1)).findAll();
        }
    }

    @Nested
    class BuscarItemMenuPorId {
        @Test
        void deveRetornarItemMenuPorId() {
            Usuario usuario = gerarUsuario();
            Endereco endereco = gerarEndereco();
            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);

            when(itemMenuRepository.findById(itemMenu.getId())).thenReturn(java.util.Optional.of(itemMenu));

            var response = usecase.buscaPorId(itemMenu.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(ItemMenuResponse.fromEntity(itemMenu));

            verify(itemMenuRepository, times(1)).findById(itemMenu.getId());
        }

        @Test
        void deveRetornarNotFoundQuandoItemMenuNaoExistir() {
            var itemMenuId = 1L;

            when(itemMenuRepository.findById(itemMenuId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> usecase.buscaPorId(itemMenuId))
                    .isInstanceOf(RecursoNaoEncontradoException.class)
                    .hasMessage( "Id " + itemMenuId +  " não foi encontrado.");

            verify(itemMenuRepository, times(1)).findById(itemMenuId);
        }
    }
}
