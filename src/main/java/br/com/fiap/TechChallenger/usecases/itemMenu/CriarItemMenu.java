package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.Restaurante;
import br.com.fiap.TechChallenger.domains.TipoUsuario;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.gateways.repository.RestauranteRepository;
import br.com.fiap.TechChallenger.usecases.exception.TipoUsuarioInvalidoException;
import br.com.fiap.TechChallenger.usecases.exception.UsuarioNaoEncontradoException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static br.com.fiap.TechChallenger.domains.TipoUsuario.DONO_RESTAURANTE;

@Service
@AllArgsConstructor
public class CriarItemMenu {

    private final ItemMenuRepository itemMenuRepository;
    private final RestauranteRepository restauranteRepository;

    public ResponseEntity<ItemMenuResponse> criar(final ItemMenuDTO dto){

        try{
            Restaurante restaurante = restauranteRepository.findById(dto.getIdRestaurante())
                    .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));;

            final ItemMenu item = ItemMenu.builder()
                    .nomePrato(dto.getNomePrato())
                    .descricaoPrato(dto.getDescricaoPrato())
                    .preco(dto.getPreco())
                    .urlImagem(dto.getUrlImagem())
                    .ingredientes(new HashSet<>(dto.getIngredientes()))
                    .alergenos(new HashSet<>(Optional.ofNullable(dto.getAlergenos()).orElse(Set.of())))
                    .disponivel(dto.isDisponivel())
                    .restaurante(restaurante)
                    .build();

            ItemMenu novoMenu = itemMenuRepository.save(item);
            ItemMenuResponse response = ItemMenuResponse.fromEntity(novoMenu);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch(Exception e) {
            System.out.println("Erro ao criar item de menu: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
