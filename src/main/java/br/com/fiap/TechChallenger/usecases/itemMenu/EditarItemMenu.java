package br.com.fiap.TechChallenger.usecases.itemMenu;

import br.com.fiap.TechChallenger.domains.Alergeno;
import br.com.fiap.TechChallenger.domains.ItemMenu;
import br.com.fiap.TechChallenger.domains.dto.ItemMenuDTO;
import br.com.fiap.TechChallenger.domains.dto.UsuarioLogado;
import br.com.fiap.TechChallenger.domains.dto.response.ItemMenuResponse;
import br.com.fiap.TechChallenger.gateways.repository.ItemMenuRepository;
import br.com.fiap.TechChallenger.usecases.exception.RecursoNaoEncontradoException;
import br.com.fiap.TechChallenger.usecases.security.Autenticacao;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class EditarItemMenu {

    private final ItemMenuRepository itemMenuRepository;

    public ResponseEntity<ItemMenuResponse> editar(Long id, ItemMenuDTO dto) {

        try {

            ItemMenu itemMenu = itemMenuRepository.findById(id)
                    .orElseThrow(() -> new RecursoNaoEncontradoException(
                            "Id " + id + " não foi encontrado."
                    ));

            itemMenu.setNomePrato(dto.getNomePrato());
            itemMenu.setDescricaoPrato(dto.getDescricaoPrato());
            itemMenu.setPreco(dto.getPreco());
            itemMenu.setUrlImagem(dto.getUrlImagem());

            boolean statusAtual = itemMenu.isDisponivel();
            boolean statusRecebido = dto.isDisponivel();

            if (statusAtual && !statusRecebido) {
                itemMenu.marcarComoIndisponivel();
            }

            if (!statusAtual && statusRecebido) {
                itemMenu.marcarComoDisponivel();
            }

            if (dto.getIngredientes() != null) {
                Set<String> ingredientesAtuais = itemMenu.getIngredientes();
                Set<String> novosIngredientes = dto.getIngredientes();

                ingredientesAtuais.removeIf(ingrediente -> !novosIngredientes.contains(ingrediente));

                novosIngredientes.stream()
                        .filter(ingrediente -> !ingredientesAtuais.contains(ingrediente))
                        .forEach(ingredientesAtuais::add);
            }

            if (dto.getAlergenos() != null) {
                Set<Alergeno> alergenosAtuais = itemMenu.getAlergenos();
                Set<Alergeno> novosAlergenos = dto.getAlergenos();

                alergenosAtuais.removeIf(alergeno -> !novosAlergenos.contains(alergeno));
                novosAlergenos.stream()
                        .filter(alergeno -> !alergenosAtuais.contains(alergeno))
                        .forEach(alergenosAtuais::add);
            }

            itemMenuRepository.save(itemMenu);

            return ResponseEntity.ok(ItemMenuResponse.fromEntity(itemMenu));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
