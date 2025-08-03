package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.*;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static br.com.fiap.TechChallenger.helpers.EnderecoHelper.gerarEndereco;
import static br.com.fiap.TechChallenger.helpers.ItensMenuHelper.gerarItemMenu;
import static br.com.fiap.TechChallenger.helpers.ItensMenuHelper.gerarItemMenuDTO;
import static br.com.fiap.TechChallenger.helpers.RestauranteHelper.gerarRestaurante;
import static br.com.fiap.TechChallenger.helpers.UsuarioHelper.gerarUsuarioDonoDeRestaurante;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Profile("teste")
public class EditarItemMenuTest {
    @Mock
    private ItemMenuRepository itemMenuRepository;

    private EditarItemMenu usecase;

    AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        usecase = new EditarItemMenu(itemMenuRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class EditarItemMenuAPI {
        @Test
        void devePermitirEditarItemMenuCompletos() {
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            Endereco endereco = gerarEndereco();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);
            itemMenu.marcarComoIndisponivel();
            Long id = 1L;

            ItemMenu itemExistente = ItemMenu.builder()
                    .id(id)
                    .nomePrato("Prato Antigo")
                    .descricaoPrato("Descrição antiga")
                    .preco(new BigDecimal("25.00"))
                    .urlImagem("imagem_antiga.jpg")
                    .disponivel(true)
                    .ingredientes(new HashSet<>(List.of("Tomate", "Presunto")))
                    .alergenos(new HashSet<>(List.of(Alergeno.GLÚTEN, Alergeno.SOJA)))
                    .restaurante(restaurante)
                    .build();

            ItemMenuDTO dto = gerarItemMenuDTO(itemMenu);

            when(itemMenuRepository.findById(id)).thenReturn(Optional.of(itemExistente));
            when(itemMenuRepository.save(any(ItemMenu.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<ItemMenuResponse> response = usecase.editar(id, dto);


            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assertThat(response.getBody())
                    .isNotNull()
                    .isInstanceOf(ItemMenuResponse.class)
                    .extracting(ItemMenuResponse::nomePrato, ItemMenuResponse::descricaoPrato, ItemMenuResponse::preco)
                    .contains(itemMenu.getNomePrato(), itemMenu.getDescricaoPrato(), itemMenu.getPreco());

            assertThat(response.getBody())
                    .isNotNull()
                    .extracting(ItemMenuResponse::disponivel)
                    .isEqualTo(false);

            assertThat(response.getBody().ingredientes()).isEmpty();
            assertThat(response.getBody().alergenos()).isEmpty();
        }

        @Test
        void devePermitirEditarItemMenuMudandoParaIndisponivel() {
            Usuario usuario = gerarUsuarioDonoDeRestaurante();
            Endereco endereco = gerarEndereco();

            Restaurante restaurante = gerarRestaurante(endereco, usuario, List.of());
            ItemMenu itemMenu = gerarItemMenu(restaurante);
            itemMenu.marcarComoDisponivel();

            Long id = 1L;

            ItemMenu itemExistente = ItemMenu.builder()
                    .id(id)
                    .nomePrato("Prato Antigo")
                    .descricaoPrato("Descrição antiga")
                    .preco(new BigDecimal("25.00"))
                    .urlImagem("imagem_antiga.jpg")
                    .disponivel(false)
                    .ingredientes(new HashSet<>(List.of("Tomate", "Presunto")))
                    .alergenos(new HashSet<>(List.of(Alergeno.GLÚTEN, Alergeno.SOJA)))
                    .restaurante(restaurante)
                    .build();

            ItemMenuDTO dto = gerarItemMenuDTO(itemMenu);

            when(itemMenuRepository.findById(id)).thenReturn(Optional.of(itemExistente));
            when(itemMenuRepository.save(any(ItemMenu.class))).thenAnswer(invocation -> invocation.getArgument(0));

            ResponseEntity<ItemMenuResponse> response = usecase.editar(id, dto);


            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

            assertThat(response.getBody())
                    .isNotNull()
                    .isInstanceOf(ItemMenuResponse.class)
                    .extracting(ItemMenuResponse::nomePrato, ItemMenuResponse::descricaoPrato, ItemMenuResponse::preco)
                    .contains(itemMenu.getNomePrato(), itemMenu.getDescricaoPrato(), itemMenu.getPreco());

            assertThat(response.getBody())
                    .isNotNull()
                    .extracting(ItemMenuResponse::disponivel)
                    .isEqualTo(true);

            assertThat(response.getBody().ingredientes()).isEmpty();
            assertThat(response.getBody().alergenos()).isEmpty();
        }

        @Test
        void deveLancarExcecaoQuandoItemNaoExistir() {
            Long id = 1L;
            ItemMenuDTO dto = gerarItemMenuDTO(gerarItemMenu(gerarRestaurante(gerarEndereco(), gerarUsuarioDonoDeRestaurante(), List.of())));

            when(itemMenuRepository.findById(id)).thenReturn(Optional.empty());

            ResponseEntity<ItemMenuResponse> response = usecase.editar(id, dto);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        }
    }
}
