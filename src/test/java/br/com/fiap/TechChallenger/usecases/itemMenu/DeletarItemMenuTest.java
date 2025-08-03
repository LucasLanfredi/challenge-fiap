package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.Endereco;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.Usuario;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
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
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuarioDonoDeRestaurante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Profile("teste")
public class DeletarItemMenuTest {
    @Mock
    private ItemMenuRepository itemMenuRepository;

    private DeletarItemMenu usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new DeletarItemMenu(itemMenuRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class DeletarItemMenuAPI {
        @Test
        void devePermitirDeletarItemMenu()   {
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            Endereco endereco = gerarEndereco();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);
            when(itemMenuRepository.findById(itemMenu.getId())).thenReturn(Optional.of(itemMenu));

            var response = usecase.deletar(itemMenu.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

            verify(itemMenuRepository, times(1)).findById(itemMenu.getId());
            verify(itemMenuRepository, times(1)).delete(itemMenu);
        }

        @Test
        void deveLancarExcecaoQuandoItemMenuNaoExiste() {
            Usuario usuario = gerarUsuario();
            Endereco endereco = gerarEndereco();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);
            when(itemMenuRepository.findById(itemMenu.getId())).thenReturn(Optional.empty());

            var response = usecase.deletar(itemMenu.getId());

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);

            verify(itemMenuRepository, times(1)).findById(itemMenu.getId());
            verify(itemMenuRepository, never()).delete(itemMenu);
        }
    }
}
